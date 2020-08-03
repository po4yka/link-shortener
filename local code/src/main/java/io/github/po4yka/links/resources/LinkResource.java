package io.github.po4yka.links.resources;

import com.mongodb.MongoClient;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Iterator;

@Path("links")
public class LinkResource {
    private static final String URL_KEY = "url";
    private static final String ID_KEY = "id";
    private static final Response ANSWER_404 = Response.status(Response.Status.NOT_FOUND).build();
    private static final MongoCollection<Document> LINKS_COLLECTION;
    private static final int MAX_NUMBER_OF_ATTEMPTS = 10;

    static {
        final MongoClient mongo = new MongoClient(); //localhost::27017
        final MongoDatabase db = mongo.getDatabase("shortened");
        LINKS_COLLECTION = db.getCollection("links");
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{id}")
    public Response getUrlById(final @PathParam("id") String id) {
        if (id == null || id.isEmpty()) {
            return ANSWER_404;
        }
        final Iterator<Document> resultIterator = LINKS_COLLECTION.find(new Document(ID_KEY, id)).iterator();
        if (!resultIterator.hasNext()) {
            return ANSWER_404;
        }
        final String url = resultIterator.next().getString(URL_KEY);
        if (url == null || "".equals(url)) {
            return ANSWER_404;
        }
        return Response.ok(url).build();
    }

    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response shortUrl(final String url) {
        if (url == null || url.isEmpty()) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        int attempt = 0;
        while (attempt < MAX_NUMBER_OF_ATTEMPTS) {
            final String id = LinkGenerator.getRandomId();
            final Document newShortedLink = new Document(ID_KEY, id);
            newShortedLink.put(URL_KEY, url);
            try {
                LINKS_COLLECTION.insertOne(newShortedLink);
                return Response.ok(id).build();
            } catch (MongoWriteException e) {
                System.out.println("Exception! ID already exists! Common number of attempts : "
                        + MAX_NUMBER_OF_ATTEMPTS + " Attempt number : " + attempt);
            }
            attempt++;
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
