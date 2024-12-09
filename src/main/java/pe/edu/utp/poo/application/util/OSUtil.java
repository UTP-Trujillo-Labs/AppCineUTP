package pe.edu.utp.poo.application.util;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class OSUtil {
    public static void mostrarArchivo(String rutaArchivo) throws IOException {
        File archivo = new File(rutaArchivo);
        String os = System.getProperty("os.name").toLowerCase();
        String commando = "";

        if (os.contains("win")) {
            commando = "explorer /select,";
        } else if (os.contains("mac")) {
            commando = "open -R ";
        } else if (os.contains("nix") || os.contains("nux")) {
            commando = "xdg-open ";
        }

        if (!commando.isEmpty()) {
            commando = commando + archivo.getAbsolutePath();
            Runtime.getRuntime().exec(commando);
        } else if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(archivo);
        }
    }
}
