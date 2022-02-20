package com.hepsiburada.ecommerce.utils;

import com.hepsiburada.ecommerce.dto.output.BaseOutput;
import com.hepsiburada.ecommerce.service.ProcessCommandService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.tool.schema.spi.CommandAcceptanceException;

import java.io.*;
import java.util.MissingFormatArgumentException;

@Getter
@Setter
@RequiredArgsConstructor
public class FileOperations {

    private String inputFile;

    private String outputFile;

    private final ProcessCommandService processCommandService;

    public void readFile() {
        File outFile = new File(outputFile);
        if(outFile.exists())
            outFile.delete();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(inputFile));
            String line = reader.readLine();
            while (line != null) {
                processLine(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeFile(String text){
        try(FileWriter fw = new FileWriter(outputFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processLine(String line) {
        try {
            String[] parts = line.split(" ");
            String command = parts[0];
            BaseOutput baseOutput = null;
            if (command.equalsIgnoreCase("create_product")) {
                if (parts.length != 4)
                    throw new MissingFormatArgumentException(line);
                baseOutput = processCommandService.callCreateProductService(parts);
            } else if (command.equalsIgnoreCase("get_product_info")) {
                if (parts.length != 2)
                    throw new MissingFormatArgumentException(line);
                baseOutput = processCommandService.callGetProductInfoService(parts);
            } else if (command.equalsIgnoreCase("create_order")) {
                if (parts.length != 3)
                    throw new MissingFormatArgumentException(line);
                baseOutput = processCommandService.callCreateProductOrderService(parts);
            } else if (command.equalsIgnoreCase("create_campaign")) {
                if (parts.length != 6)
                    throw new MissingFormatArgumentException(line);
                baseOutput = processCommandService.callCreateCampaignService(parts);
            } else if (command.equalsIgnoreCase("get_campaign_info")) {
                if (parts.length != 2)
                    throw new MissingFormatArgumentException(line);
                baseOutput = processCommandService.callGetCampaignService(parts);
            } else if (command.equalsIgnoreCase("increase_time")) {
                if (parts.length != 2)
                    throw new MissingFormatArgumentException(line);
                baseOutput = processCommandService.callIncreaseTimeService(parts);
            }
                if(baseOutput != null)
                    writeFile(baseOutput.getReturnMessage());
        } catch (MissingFormatArgumentException | NumberFormatException | CommandAcceptanceException e) {
            writeFile("ERROR - Command is not valid: " + line);
        }
    }

}
