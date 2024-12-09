package pe.edu.utp.poo.application.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import pe.edu.utp.poo.application.common.Util;
import pe.edu.utp.poo.application.model.ReporteVenta;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static pe.edu.utp.poo.application.common.Constant.FILE_LOCALDATETIME_FORMAT;

public class ExcelUtil {
    public static String export(String rutaDestino, List<ReporteVenta> reporte) throws IOException {

        String fechaArchivo = Util.dateToString(LocalDateTime.now(), FILE_LOCALDATETIME_FORMAT);
        File outputFile = new File(rutaDestino, "ReporteVentas_" + fechaArchivo + ".xlsx");

        try (
                Workbook workbook = new SXSSFWorkbook(100);
                FileOutputStream fileOut = new FileOutputStream(outputFile);
        ) {
            workbook.createSheet("Reporte Ventas");
            int fila = 0;
            Sheet hojaReporteVentas = workbook.getSheetAt(0);

            CellStyle estiloCabecera = workbook.createCellStyle();

            estiloCabecera.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            estiloCabecera.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Font font = workbook.createFont();
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            estiloCabecera.setFont(font);

            estiloCabecera.setAlignment(HorizontalAlignment.CENTER);
            estiloCabecera.setVerticalAlignment(VerticalAlignment.CENTER);

            Row filaCabecera = hojaReporteVentas.createRow(fila);
            String[] cabecera = {"Id Venta", "Nombre Cliente", "Nombre Vendedor", "Título Película", "Horario", "Fecha Venta", "Cantidad Tickets", "Subtotal"};

            int columna = 0;
            for (String titulo : cabecera) {
                Cell celda = filaCabecera.createCell(columna);
                celda.setCellValue(titulo);
                celda.setCellStyle(estiloCabecera);
                columna++;
            }

            fila++;

            for (ReporteVenta venta : reporte) {
                Row filaActual = hojaReporteVentas.createRow(fila);
                filaActual.createCell(0).setCellValue(venta.getVentaId());
                filaActual.createCell(1).setCellValue(venta.getNombreCliente());
                filaActual.createCell(2).setCellValue(venta.getNombreUsuario());
                filaActual.createCell(3).setCellValue(venta.getTituloPelicula());
                filaActual.createCell(4).setCellValue(venta.getHorario());
                filaActual.createCell(5).setCellValue(Util.dateToString(venta.getFechaVenta()));
                filaActual.createCell(6).setCellValue(venta.getCantidadTickets());
                filaActual.createCell(7).setCellValue(venta.getSubtotal());
                fila++;
            }

            workbook.write(fileOut);
        }
        return outputFile.getAbsolutePath();
    }
}
