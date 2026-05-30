package servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

public abstract class BaseServlet extends HttpServlet {

    protected void writePage(HttpServletResponse response, String title, String body) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
            out.println("<title>" + escape(title) + "</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p><a href=\"index.html\">Kembali ke Home</a></p>");
            out.println("<h1>" + escape(title) + "</h1>");
            out.println(body);
            out.println("</body>");
            out.println("</html>");
        }
    }

    protected void writeError(HttpServletResponse response, Exception ex) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        writePage(response, "Terjadi Kesalahan", "<p>" + escape(ex.getMessage()) + "</p>");
    }

    protected String param(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        return value == null ? "" : value.trim();
    }

    protected int intParam(HttpServletRequest request, String name) {
        return Integer.parseInt(param(request, name));
    }

    protected double doubleParam(HttpServletRequest request, String name) {
        return Double.parseDouble(param(request, name));
    }

    protected String dateParam(HttpServletRequest request, String name) {
        String value = param(request, name);
        if (value.isEmpty()) {
            return LocalDate.now().toString();
        }
        return value.length() >= 10 ? value.substring(0, 10) : value;
    }

    protected String escape(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
