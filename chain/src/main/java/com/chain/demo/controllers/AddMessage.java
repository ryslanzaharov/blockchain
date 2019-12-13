package com.chain.demo.controllers;

import com.chain.demo.models.Block;
import com.chain.demo.repository.BlockDataRepository;
import com.google.gson.GsonBuilder;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Properties;

import static com.chain.demo.algorithm.NoobChain.*;

@Controller
public class AddMessage {

    private static final Logger Log = LoggerFactory.getLogger(AddMessage.class);

    private String filePath;

    private final BlockDataRepository blockDataRepository;

    public AddMessage(BlockDataRepository blockDataRepository) {
        this.blockDataRepository = blockDataRepository;
    }


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String getPageForAddMessage(ModelMap model) {
        List<Block> blocks = blockDataRepository.getAllBlock();
        model.addAttribute("chainvalid", isChainValid(blocks));
        model.addAttribute("blockchain", blocks);
        return "index";
    }


    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public String addMessage(
            @RequestParam(value = "message") String message,
            @RequestParam("file") MultipartFile uploadingFile) {
        List<Block> blocks = blockDataRepository.getAllBlock();
        if (!message.isEmpty()) {
            if (blocks.size() == 0) {
                Block block = new Block(message, "0");
                block.mineBlock(difficulty);
                blockDataRepository.save(block);
            } else {
                Block block = new Block(message, blocks.get(blocks.size() - 1).hash);
                block.mineBlock(difficulty);
                blockDataRepository.save(block);
            }
        }
        // Загрузка файла
        else if (!uploadingFile.isEmpty()) {
            try {
                Properties properties = new Properties();
                properties.load(AddMessage.class.getClassLoader().getResourceAsStream("fp.properties"));
                this.filePath = properties.getProperty("FILE_PATH");
                File file = new File(filePath + uploadingFile.getOriginalFilename());
                uploadingFile.transferTo(file);
                if (blocks.size() == 0) {
                    Block block = new Block(file.getPath(), "0");
                    block.mineBlock(difficulty);
                    blockDataRepository.save(block);
                } else {
                    Block block = new Block(file.getPath(), blocks.get(blocks.size() - 1).hash);
                    block.mineBlock(difficulty);
                    blockDataRepository.save(block);
                }
            } catch (Exception e) {
                Log.error(e.getMessage(), e);
            }
        }
        return "redirect:index";
    }
}
