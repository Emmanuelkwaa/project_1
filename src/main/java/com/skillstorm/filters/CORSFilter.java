package com.skillstorm.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class CORSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("CORS Filter at work");
    }

    @Override
    public void destroy() {
        System.out.println("CORS Filter work is done");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("CORS FIlter filtering");
        HttpServletResponse res = (HttpServletResponse) response;
        res.addHeader("Access-Control-Allow-Origin", "*"); // Allows any domain to make a request
        res.addHeader("Access-Control-Allow-Methods", "*"); // Allows all HTTP methods
        res.addHeader("Access-Control-Allows-Credentials", "true");
        res.addHeader("Access-Control-Allow-Headers", "*"); // Allows all types of headers

        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getMethod().equals("OPTIONS")) {
            res.setStatus(202);
        }
        chain.doFilter(req, res);
    }
}
