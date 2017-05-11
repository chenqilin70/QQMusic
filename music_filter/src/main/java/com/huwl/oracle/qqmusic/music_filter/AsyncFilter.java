package com.huwl.oracle.qqmusic.music_filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebFilter(filterName="AsyncFilter",urlPatterns="/ReverseAjaxServlet",asyncSupported=true)
public class AsyncFilter extends HttpFilter {

	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) {
		try {
			request.getRequestDispatcher("/ReverseAjaxServlet").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
