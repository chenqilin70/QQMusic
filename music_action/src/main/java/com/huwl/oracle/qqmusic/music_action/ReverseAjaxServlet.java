package com.huwl.oracle.qqmusic.music_action;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.jws.WebService;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet(value="/ReverseAjaxServlet", asyncSupported=true)
public class ReverseAjaxServlet extends HttpServlet {
	private Queue<AsyncContext> CONNECTIONS=new ConcurrentLinkedQueue<AsyncContext>();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String method=req.getParameter("method");
		System.out.println("/ReverseAjaxServlet is running");
		if("onOpen".equals(method)){
			onOpen(req,resp);
		}
	}

	private void onOpen(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("onOpen is running");
		AsyncContext context=req.startAsync();
		context.setTimeout(0);
		CONNECTIONS.offer(context);
	}
	
}
