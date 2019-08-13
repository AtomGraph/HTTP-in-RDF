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
package com.atomgraph.httpinrdf.vocabulary;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;

/**
 *
 * @author Martynas Jusevičius <martynas@atomgraph.com>
 */
public class HTTPinRDF
{
    
    /** <p>The RDF model that holds the vocabulary terms</p> */
    private static OntModel m_model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
    
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://www.w3.org/2011/http#";
    
    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI()
    {
	return NS;
    }
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );

    public static final OntClass Request = m_model.createClass( NS + "Request" );

    public static final OntClass Connection = m_model.createClass( NS + "Connection" );

    public static final OntClass RequestHeader = m_model.createClass( NS + "RequestHeader" );
    
    public static final OntClass ResponseHeader = m_model.createClass( NS + "ResponseHeader" );

    public static final DatatypeProperty httpVersion = m_model.createDatatypeProperty( NS + "httpVersion" );
    
    public static final ObjectProperty mthd = m_model.createObjectProperty( NS + "mthd" );
    
    public static final ObjectProperty requests = m_model.createObjectProperty( NS + "requests" );

    public static final ObjectProperty resp = m_model.createObjectProperty( NS + "resp" );

    public static final ObjectProperty headers = m_model.createObjectProperty( NS + "headers" );
    
    public static final DatatypeProperty methodName = m_model.createDatatypeProperty( NS + "methodName" );
    
    public static final DatatypeProperty absolutePath = m_model.createDatatypeProperty( NS + "absolutePath" );
    
    public static final DatatypeProperty fieldName = m_model.createDatatypeProperty( NS + "fieldName" );
    
    public static final DatatypeProperty fieldValue = m_model.createDatatypeProperty( NS + "fieldValue" );

    public static final DatatypeProperty statusCodeValue = m_model.createDatatypeProperty( NS + "statusCodeValue" );
    
    public static final ObjectProperty sc = m_model.createObjectProperty( NS + "sc" );

    public static final DatatypeProperty reasonPhrase = m_model.createDatatypeProperty( NS + "reasonPhrase" );

}
