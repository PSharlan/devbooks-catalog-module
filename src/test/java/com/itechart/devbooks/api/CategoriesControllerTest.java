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


public class CategoriesControllerTest extends AbstractControllerTest {

    @Test
    public void getAllCategories() throws Exception {
        this.mockMvc.perform(get("/api/v1/catalog/categories"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("cat1")))
                .andExpect(content().string(containsString("cat2")))
                .andExpect(content().string(containsString("cat3")))
                .andExpect(content().string(containsString("cat4")))
                .andExpect(content().string(containsString("cat5")));
    }

    @Test
    public void getCategoryById() throws Exception {
        this.mockMvc.perform(get("/api/v1/catalog/categories/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("cat2")));
    }

    @Test
    public void notFoundCategoryById() throws Exception {
        this.mockMvc.perform(get("/api/v1/catalog/categories/00"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void createNewCategoryAndDelete() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/api/v1/catalog/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"cat9\"}"))
                .andReturn();

        String foundStr = result.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(foundStr, Map.class);
        int id = (Integer) map.get("id");

        this.mockMvc.perform(get("/api/v1/catalog/categories/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("cat9")));

        this.mockMvc.perform(delete("/api/v1/catalog/categories/" + id))
                .andDo(print())
                .andExpect(status().isNoContent());

        this.mockMvc.perform(get("/api/v1/catalog/categories/" + id))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }


    @Test
    public void updateCategory() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/api/v1/catalog/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"cat11\"}"))
                .andReturn();

        String foundStr = result.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(foundStr, Map.class);
        int id = (Integer) map.get("id");

        this.mockMvc.perform(put("/api/v1/catalog/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"" + id + "\",\"name\":\"updated_cat11\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("updated_cat11")));

        this.mockMvc.perform(delete("/api/v1/catalog/categories/" + id))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateNotExistingCategory() throws Exception {
        this.mockMvc.perform(put("/api/v1/catalog/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"cat44\"}"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}
