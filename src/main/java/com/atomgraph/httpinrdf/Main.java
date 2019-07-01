package com.atomgraph.httpinrdf;

/**
 *  Copyright 2018 Martynas Jusevičius <martynas@atomgraph.com>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
import com.atomgraph.httpinrdf.vocabulary.HTTPinRDF;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import static java.lang.System.exit;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFList;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.vocabulary.RDF;

/**
 * A HTTP-in-RDF processor. Reads request metadata as RDF and writes response metadata as RDF.
 * 
 * @author Martynas Jusevičius <martynas@atomgraph.com>
 * @see <a href="https://www.w3.org/TR/HTTP-in-RDF10/">HTTP Vocabulary in RDF 1.0</a>
 */
public class Main
{

    public static final Resource createConnection()
    {
        return ModelFactory.createDefaultModel().createResource().
            addProperty(RDF.type, HTTPinRDF.Connection);
    }
    
    public static final Resource createRequest(Resource conn, String method, String host, String path, String accept)
    {
        RDFList headers = conn.getModel().createList();
        Resource request = conn.getModel().createResource().
            addProperty(RDF.type, HTTPinRDF.Request).
            addProperty(HTTPinRDF.mthd, ResourceFactory.createResource("http://www.w3.org/2011/http-methods#" + method)).
            addLiteral(HTTPinRDF.methodName, method).
            addLiteral(HTTPinRDF.absolutePath, path);

        headers = withRequestHeader(headers, HttpHeaders.HOST, host);
        headers = withRequestHeader(headers, HttpHeaders.ACCEPT, accept);
        
        return request.addProperty(HTTPinRDF.headers, headers);
    }
    
    public static final RDFList withRequestHeader(RDFList headers, String fieldName, String fieldValue)
    {
        Resource header =  headers.getModel().createResource().
            addProperty(RDF.type, HTTPinRDF.RequestHeader).
            addLiteral(HTTPinRDF.fieldName, fieldName).
            addLiteral(HTTPinRDF.fieldValue, fieldValue);
        
        return headers.with(header);
    }

    public static final RDFList withResponseHeader(RDFList headers, String fieldName, String fieldValue)
    {
        Resource header =  headers.getModel().createResource().
            addProperty(RDF.type, HTTPinRDF.ResponseHeader).
            addLiteral(HTTPinRDF.fieldName, fieldName).
            addLiteral(HTTPinRDF.fieldValue, fieldValue);
        
        return headers.with(header);
    }

    
    public static final Resource execute(Client client, Resource request) throws URISyntaxException
    {
        RDFList reqHeaders = request.getProperty(HTTPinRDF.headers).getResource().as(RDFList.class);

        String path = request.getProperty(HTTPinRDF.absolutePath).getString();
        String host = null;
        ExtendedIterator<RDFNode> hostIt = reqHeaders.iterator();
        while (hostIt.hasNext())
        {
            Resource header = hostIt.next().asResource();
            String fieldName = header.getProperty(HTTPinRDF.fieldName).getString();
            if (fieldName.equals(HttpHeaders.HOST)) host = header.getProperty(HTTPinRDF.fieldValue).getString();
        }
        
        String accept = null;
        ExtendedIterator<RDFNode> acceptIt = reqHeaders.iterator();
        while (acceptIt.hasNext())
        {
            Resource header = acceptIt.next().asResource();
            String fieldName = header.getProperty(HTTPinRDF.fieldName).getString();
            if (fieldName.equals(HttpHeaders.ACCEPT)) accept = header.getProperty(HTTPinRDF.fieldValue).getString();
        }
        URI uri = new URI("https", null, host, 443, path, null, null); // TO-DO: what about query string?
        WebResource resource = client.resource(uri);
                
        ClientResponse cr = null;
        try
        {
            ExtendedIterator<RDFNode> reqHeaderIt = reqHeaders.iterator();
            while (reqHeaderIt.hasNext())
            {
                Resource header = reqHeaderIt.next().asResource();
                String name = header.getProperty(HTTPinRDF.fieldName).getString();
                String value = header.getProperty(HTTPinRDF.fieldValue).getString();
                resource.header(name, value);
            }
            cr = resource.accept(accept).get(ClientResponse.class);

            Resource response = request.getModel().createResource().
                    addLiteral(HTTPinRDF.statusCodeValue, cr.getStatusInfo().getStatusCode()).
                    addLiteral(HTTPinRDF.reasonPhrase, cr.getStatusInfo().getReasonPhrase());
                    
            RDFList respHeaders = response.getModel().createList();
            MultivaluedMap<String, String> respHeadersMap = cr.getHeaders();
            Iterator<Entry<String, List<String>>> respHeaderIt = respHeadersMap.entrySet().iterator();
            while (respHeaderIt.hasNext())
            {
                Entry<String, List<String>> entry = respHeaderIt.next();
                String header = entry.getKey();
                for (String value : entry.getValue())
                    respHeaders = withResponseHeader(respHeaders, header, value);
            }
            
            return response.addProperty(HTTPinRDF.headers, respHeaders);
        }
        finally
        {
            if (cr != null) cr.close();
        }
    }
    
    public static void main(String[] args) throws MalformedURLException, URISyntaxException
    {
        if (args.length != 2)
        {
            System.err.println("Usage: url accept");
            System.err.println("Example: https://www.example.org/image \"image/png, image/gif;q=0.8\"");
            exit(1);
        }
            
        URL url = new URL(args[0]);
        String accept = args[1];
        
        Resource conn = createConnection();
        Resource req = createRequest(conn, "GET", url.getHost(), url.getPath(), accept);
        RDFList requests = conn.getModel().createList().with(req);
        conn.addProperty(HTTPinRDF.requests, requests);
        
        Resource request = conn.getPropertyResourceValue(HTTPinRDF.requests).as(RDFList.class).getHead().asResource(); // first request

        Client client = Client.create();
        Resource response = execute(client, request);
        request.addProperty(HTTPinRDF.resp, response);
        
        response.getModel().write(System.out, Lang.TURTLE.getName());
    }
    
}
