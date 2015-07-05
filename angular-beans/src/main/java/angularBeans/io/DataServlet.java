package angularBeans.io;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/lob/*")
public class DataServlet extends HttpServlet {

	@Inject
	private ByteArrayCache cache;

	public DataServlet() {

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse response) {

		String requestURI = req.getRequestURI();

		int index = (requestURI.indexOf("/lob")) + 5;
		String resourceId = requestURI.substring(index);

		response.setHeader("Access-Control-Allow-Origin", "*");
		
		byte[] data = null ;
		OutputStream o;
		try {
		if (cache.getCache().containsKey(resourceId)) {
			Call call = cache.getCache().get(resourceId);
			Method m = call.getMethod();
			Object container = call.getObject();

			
		

				
				Object result = m.invoke(container);

				if(result!=null){
				 data = ((LobWrapper) result).getData();
				 
				}

		}
				else{
					if(cache.getTempCache().containsKey(resourceId)){
						data=cache.getTempCache().get(resourceId);
						cache.getTempCache().remove(resourceId);
					}	
					}
				

				o = response.getOutputStream();
				if (data == null) {
					data = "default".getBytes();
				}
				o.write(data);
				o.flush();
				o.close();
			
				
		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	
		
		
	}

}
