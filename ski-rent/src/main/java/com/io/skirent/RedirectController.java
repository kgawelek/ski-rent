package com.io.skirent;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class RedirectController {

    @GetMapping("/index")
    public void home(HttpServletResponse httpResponse) {
        try {
            httpResponse.sendRedirect("/index.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
