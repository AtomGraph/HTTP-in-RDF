# HTTP-in-RDF

Basic [HTTP Vocabulary in RDF 1.0](https://www.w3.org/TR/HTTP-in-RDF10/) processor.

## Usage

    java -jar http-in-rdf-1.0.0-SNAPSHOT-jar-with-dependencies.jar URI AcceptHeader

## Example

Command:

    java -jar http-in-rdf-1.0.0-SNAPSHOT-jar-with-dependencies.jar https://atomgraph.com "text/turtle"

Result as Turtle:

```turtle
_:b0    a       <http://www.w3.org/2011/http#ResponseHeader> ;
        <http://www.w3.org/2011/http#fieldName>
                "Link" ;
        <http://www.w3.org/2011/http#fieldValue>
                "<https://atomgraph.com/ns/templates#Root>; rel=type" .

_:b1    a       <http://www.w3.org/2011/http#ResponseHeader> ;
        <http://www.w3.org/2011/http#fieldName>
                "Date" ;
        <http://www.w3.org/2011/http#fieldValue>
                "Tue, 13 Aug 2019 10:00:10 GMT" .

[ a       <http://www.w3.org/2011/http#Connection> ;
  <http://www.w3.org/2011/http#requests>
          ( _:b2 )
] .

_:b3    a       <http://www.w3.org/2011/http#ResponseHeader> ;
        <http://www.w3.org/2011/http#fieldName>
                "Transfer-Encoding" ;
        <http://www.w3.org/2011/http#fieldValue>
                "chunked" .

_:b4    a       <http://www.w3.org/2011/http#ResponseHeader> ;
        <http://www.w3.org/2011/http#fieldName>
                "Server" ;
        <http://www.w3.org/2011/http#fieldValue>
                "Apache-Coyote/1.1" .

_:b5    a       <http://www.w3.org/2011/http#ResponseHeader> ;
        <http://www.w3.org/2011/http#fieldName>
                "Link" ;
        <http://www.w3.org/2011/http#fieldValue>
                "<https://atomgraph.com/>; rel=https://www.w3.org/ns/ldt#base" .

_:b6    a       <http://www.w3.org/2011/http#ResponseHeader> ;
        <http://www.w3.org/2011/http#fieldName>
                "Content-Type" ;
        <http://www.w3.org/2011/http#fieldValue>
                "text/turtle;charset=UTF-8" .

_:b7    a       <http://www.w3.org/2011/http#ResponseHeader> ;
        <http://www.w3.org/2011/http#fieldName>
                "Cache-Control" ;
        <http://www.w3.org/2011/http#fieldValue>
                "max-age=3600, public" .

_:b8    a       <http://www.w3.org/2011/http#RequestHeader> ;
        <http://www.w3.org/2011/http#fieldName>
                "Accept" ;
        <http://www.w3.org/2011/http#fieldValue>
                "text/turtle" .

_:b9    a       <http://www.w3.org/2011/http#ResponseHeader> ;
        <http://www.w3.org/2011/http#fieldName>
                "ETag" ;
        <http://www.w3.org/2011/http#fieldValue>
                "\"df2baeb2b4986da5\"" .

_:b10   a       <http://www.w3.org/2011/http#ResponseHeader> ;
        <http://www.w3.org/2011/http#fieldName>
                "Link" ;
        <http://www.w3.org/2011/http#fieldValue>
                "<https://atomgraph.com/ns#>; rel=https://www.w3.org/ns/ldt#ontology" .

_:b2    a       <http://www.w3.org/2011/http#Request> ;
        <http://www.w3.org/2011/http#absolutePath>
                "" ;
        <http://www.w3.org/2011/http#headers>
                ( _:b11 _:b8 ) ;
        <http://www.w3.org/2011/http#methodName>
                "GET" ;
        <http://www.w3.org/2011/http#mthd>
                <http://www.w3.org/2011/http-methods#GET> ;
        <http://www.w3.org/2011/http#resp>
                [ <http://www.w3.org/2011/http#headers>
                          ( _:b3 _:b7 _:b12 _:b4 _:b9 _:b13 _:b14 _:b1 _:b5 _:b10 _:b0 _:b6 ) ;
                  <http://www.w3.org/2011/http#reasonPhrase>
                          "OK" ;
                  <http://www.w3.org/2011/http#statusCodeValue>
                          "200"^^<http://www.w3.org/2001/XMLSchema#long>
                ] .

_:b14   a       <http://www.w3.org/2011/http#ResponseHeader> ;
        <http://www.w3.org/2011/http#fieldName>
                "Expires" ;
        <http://www.w3.org/2011/http#fieldValue>
                "Thu, 01 Jan 1970 01:00:00 CET" .

_:b11   a       <http://www.w3.org/2011/http#RequestHeader> ;
        <http://www.w3.org/2011/http#fieldName>
                "Host" ;
        <http://www.w3.org/2011/http#fieldValue>
                "atomgraph.com" .

_:b12   a       <http://www.w3.org/2011/http#ResponseHeader> ;
        <http://www.w3.org/2011/http#fieldName>
                "Cache-Control" ;
        <http://www.w3.org/2011/http#fieldValue>
                "private" .

_:b13   a       <http://www.w3.org/2011/http#ResponseHeader> ;
        <http://www.w3.org/2011/http#fieldName>
                "Vary" ;
        <http://www.w3.org/2011/http#fieldValue>
                "Accept-Charset,Accept" .
```