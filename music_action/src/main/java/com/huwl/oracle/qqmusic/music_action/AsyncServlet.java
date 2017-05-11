package com.huwl.oracle.qqmusic.music_action;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.jws.WebService;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value="/AsyncServlet", asyncSupported=true)
public class AsyncServlet extends HttpServlet {
	private static final Queue<AsyncContext> CONNECTIONS=new ConcurrentLinkedQueue<>();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String method=req.getParameter("method");
		if("onOpen".equals(method)){
			onOpen(req,resp);
		}else{
			onMessage(req,resp);
		}
	}
	private void onMessage(HttpServletRequest req, HttpServletResponse resp) {
		String message=req.getParameter("message");
		for(AsyncContext context:CONNECTIONS){
			HttpServletResponse response=(HttpServletResponse) context.getResponse();
			try {
				response.getWriter().print(message);
				response.getWriter().flush();
				context.complete();
				CONNECTIONS.remove(context);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	private void onOpen(HttpServletRequest req, HttpServletResponse resp) {
		AsyncContext  context=req.startAsync();
		
		context.setTimeout(0);
		CONNECTIONS.offer(context);
	}


}
