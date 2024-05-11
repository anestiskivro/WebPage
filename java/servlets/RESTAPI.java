package servlets;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import database.tables.EditBooksTable;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import mainClasses.Book;

@Path("/Librarian")
public class RESTAPI {
	
	@POST
	@Path("/books")
	@Produces({ MediaType.APPLICATION_JSON})
	public Response bookCheck(String book) throws IOException, ClassNotFoundException {
		Gson g=new Gson();
		Book b=g.fromJson(book, Book.class);
		System.out.println(b.getAuthors());
		if(b.getPages()<=0 || b.getPublicationyear()<1200 || !(b.getUrl().startsWith("http")) || !(b.getPhoto().startsWith("http"))) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		}else {
			if(b.getIsbn().length()<10 || b.getIsbn().length()>13) {
				return Response.status(Response.Status.FORBIDDEN).build();
			}
			EditBooksTable ebt=new EditBooksTable();
			ebt.addBookFromJSON(book);
			return Response.status(Response.Status.OK).build();
		}
	}

	@GET
	@Path("/booksavailable")
	@Produces({ MediaType.APPLICATION_JSON})
	public Response getBooks() throws ClassNotFoundException, SQLException {
		ArrayList<Book> books= new ArrayList<>();
		EditBooksTable ebt=new EditBooksTable();
		Gson g = new Gson();
		books=ebt.databaseToBooks();
		String json = g.toJson(books);
		return Response.status(Response.Status.OK).type("application/json").entity(json).build();
	}
}