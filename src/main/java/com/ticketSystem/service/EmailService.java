package com.ticketSystem.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.relational.core.sql.In;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from]")
    private String fromAddress;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // -- Email: asignación inicial

    public void enviarNotificacionAsignacion(String toEmail,
                                             String nombreAgente,
                                             Integer ticketId,
                                             String tituloTicket) {
        String asunto = "Ticket #" + ticketId + " asignado a ti";
        String cuerpo = construirHtmlAsignacion(nombreAgente, ticketId, tituloTicket);
        enviar(toEmail, asunto, cuerpo);
    }

    // -- Email: reasignación
    public void enviarNotificacionReasignacion(String toEmail,
                                               String nombreAgente,
                                               Integer ticketId,
                                               String tituloTicket,
                                               String motivo) {
        String asunto = "Ticket #" + ticketId + " reasignado a ti";
        String cuerpo = construirHtmlReasignacion(nombreAgente, ticketId, tituloTicket, motivo);
        enviar(toEmail, asunto, cuerpo);
    }

    // -- Interno de envio

    private void enviar(String to, String asunto, String cuerpoHtml) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setFrom(fromAddress);
            helper.setTo(to);
            helper.setSubject(asunto);
            helper.setText(cuerpoHtml, true); // true = es HTML

            mailSender.send(mensaje);


        }catch (MessagingException e) {
            throw new RuntimeException("Error al enviar email a: " + to, e);
        }
    }

    // -- Templates HTML
    private String construirHtmlAsignacion(String nombreAgente,
                                           Integer ticketId,
                                           String tituloTicket) {
        return """
                <html>
                <body style="font-family: Arial, sans-serif; color: #333;">
                    <h2 style="color: #2c7be5;">Nuevo ticket asignado</h2>
                    <p>Hola <strong>%s</strong>,</p>
                    <p>Se te ha asignado el siguiente ticket:</p>
                    <table style="border-collapse: collapse; width: 100%%;">
                        <tr>
                            <td style="padding: 8px; border: 1px solid #ddd;"><strong>ID</strong></td>
                            <td style="padding: 8px; border: 1px solid #ddd;">#%d</td>
                        </tr>
                        <tr>
                            <td style="padding: 8px; border: 1px solid #ddd;"><strong>Título</strong></td>
                            <td style="padding: 8px; border: 1px solid #ddd;">%s</td>
                        </tr>
                    </table>
                    <br/>
                    <p style="color: #666;">Sistema de Tickets — notificación automática</p>
                </body>
                </html>
                """.formatted(nombreAgente, ticketId, tituloTicket);
    }

    private String construirHtmlReasignacion(String nombreAgente,
                                             Integer ticketId,
                                             String tituloTicket,
                                             String motivo) {
        return """
                <html>
                <body style="font-family: Arial, sans-serif; color: #333;">
                    <h2 style="color: #e5a52c;">Ticket reasignado a ti</h2>
                    <p>Hola <strong>%s</strong>,</p>
                    <p>Se te ha reasignado el siguiente ticket:</p>
                    <table style="border-collapse: collapse; width: 100%%;">
                        <tr>
                            <td style="padding: 8px; border: 1px solid #ddd;"><strong>ID</strong></td>
                            <td style="padding: 8px; border: 1px solid #ddd;">#%d</td>
                        </tr>
                        <tr>
                            <td style="padding: 8px; border: 1px solid #ddd;"><strong>Título</strong></td>
                            <td style="padding: 8px; border: 1px solid #ddd;">%s</td>
                        </tr>
                        <tr>
                            <td style="padding: 8px; border: 1px solid #ddd;"><strong>Motivo</strong></td>
                            <td style="padding: 8px; border: 1px solid #ddd;">%s</td>
                        </tr>
                    </table>
                    <br/>
                    <p style="color: #666;">Sistema de Tickets — notificación automática</p>
                </body>
                </html>
                """.formatted(nombreAgente, ticketId, tituloTicket, motivo);
    }




}
