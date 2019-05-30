package com.itechart.devbooks.api;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TagsControllerTest extends AbstractControllerTest{

    @Test
    public void getAllTags() throws Exception {
        this.mockMvc.perform(get("/api/v1/catalog/tags"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("tag1")))
                .andExpect(content().string(containsString("tag2")))
                .andExpect(content().string(containsString("tag3")))
                .andExpect(content().string(containsString("tag4")))
                .andExpect(content().string(containsString("tag5")));
    }

    @Test
    public void getTagById() throws Exception {
        this.mockMvc.perform(get("/api/v1/catalog/tags/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("tag2")));
    }

    @Test
    public void notFoundTagById() throws Exception {
        this.mockMvc.perform(get("/api/v1/catalog/tags/00"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void createNewTagAndDelete() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/api/v1/catalog/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"tag9\"}"))
                .andReturn();

        String foundStr = result.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(foundStr, Map.class);
        int id = (Integer) map.get("id");

        this.mockMvc.perform(get("/api/v1/catalog/tags/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("tag9")));

        this.mockMvc.perform(delete("/api/v1/catalog/tags/" + id))
                .andDo(print())
                .andExpect(status().isNoContent());

        this.mockMvc.perform(get("/api/v1/catalog/tags/" + id))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }


    @Test
    public void updateTag() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/api/v1/catalog/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"tag11\"}"))
                .andReturn();

        String foundStr = result.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(foundStr, Map.class);
        int id = (Integer) map.get("id");

        this.mockMvc.perform(put("/api/v1/catalog/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"" + id + "\",\"name\":\"updated_tag11\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("updated_tag11")));

        this.mockMvc.perform(delete("/api/v1/catalog/tags/" + id))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateNotExistingTag() throws Exception {
        this.mockMvc.perform(put("/api/v1/catalog/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"tag44\"}"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}
