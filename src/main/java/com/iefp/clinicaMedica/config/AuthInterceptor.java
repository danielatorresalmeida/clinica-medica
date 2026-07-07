package com.iefp.clinicaMedica.config;

import com.iefp.clinicaMedica.model.Utilizador;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {

        String caminho = request.getRequestURI();

        if (rotaPublica(caminho)) {
            return true;
        }

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("utilizadorLogado") == null) {
            response.sendRedirect("/login");
            return false;
        }

        Utilizador utilizador = (Utilizador) session.getAttribute("utilizadorLogado");
        String tipo = utilizador.getTipo();

        // Todos os utilizadores com login podem entrar na home
        if (caminho.equals("/home")) {
            return true;
        }

        // Apenas ADMIN pode aceder ao registo de utilizadores
        if (caminho.equals("/registar-utilizador")
                || caminho.equals("/registar")
                || caminho.startsWith("/registo/")) {

            if ("ADMIN".equals(tipo)) {
                return true;
            }

            response.sendRedirect("/acesso-negado");
            return false;
        }

        // ADMIN pode aceder a tudo
        if ("ADMIN".equals(tipo)) {
            return true;
        }

        // SECRETARIA pode consultar e gerir áreas clínicas principais
        if ("SECRETARIA".equals(tipo)) {
            if (caminho.startsWith("/pacientes")
                    || caminho.startsWith("/medicos")
                    || caminho.startsWith("/consultas")
                    || caminho.startsWith("/exames")
                    || caminho.startsWith("/receitas")
                    || caminho.startsWith("/disponibilidades")) {
                return true;
            }
        }

        // MEDICO pode consultar pacientes e gerir as suas áreas clínicas
        if ("MEDICO".equals(tipo)) {
            if (caminho.startsWith("/pacientes")
                    || caminho.startsWith("/consultas")
                    || caminho.startsWith("/exames")
                    || caminho.startsWith("/receitas")
                    || caminho.startsWith("/disponibilidades")) {
                return true;
            }
        }

        // PACIENTE só pode consultar as suas áreas
        if ("PACIENTE".equals(tipo)) {
            if (caminho.startsWith("/consultas")
                    || caminho.startsWith("/exames")
                    || caminho.startsWith("/receitas")) {
                return true;
            }
        }

        response.sendRedirect("/acesso-negado");
        return false;
    }

    private boolean rotaPublica(String caminho) {
        return caminho.equals("/")
                || caminho.equals("/login")
                || caminho.equals("/logout")
                || caminho.equals("/acesso-negado")
                || caminho.equals("/error")
                || caminho.startsWith("/css/")
                || caminho.startsWith("/js/")
                || caminho.startsWith("/images/")
                || caminho.startsWith("/webjars/")
                || caminho.startsWith("/h2-console");
    }
}