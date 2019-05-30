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


public class OffersControllerTest extends AbstractControllerTest {

    @Test
    public void getAllOffers() throws Exception {
        this.mockMvc.perform(get("/api/v1/catalog/offers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("TestOffer1")))
                .andExpect(content().string(containsString("TestOffer2")))
                .andExpect(content().string(containsString("TestOffer3")))
                .andExpect(content().string(containsString("333.33")))
                .andExpect(content().string(containsString("cat1")));
    }

    @Test
    public void getOffersByIds() throws Exception {
        this.mockMvc.perform(get("/api/v1/catalog/offers/set?ids=1&ids=2&ids=3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("TestOffer1")))
                .andExpect(content().string(containsString("TestOffer2")))
                .andExpect(content().string(containsString("TestOffer3")));
    }

    @Test
    public void getOfferById() throws Exception {
        this.mockMvc.perform(get("/api/v1/catalog/offers/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("TestOffer2")))
                .andExpect(content().string(containsString("222.22")))
                .andExpect(content().string(containsString("cat2")))
                .andExpect(content().string(containsString("tag2")))
                .andExpect(content().string(containsString("tag3")));
    }

    @Test
    public void notFoundOfferById() throws Exception {
        this.mockMvc.perform(get("/api/v1/catalog/offers/00"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void createNewOfferAndDelete() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/api/v1/catalog/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"TestOffer4\",\"description\":\"TestDescription4\",\"category\":{\"id\":4,\"name\":\"cat4\"},\"price\":444.44,\"tags\":[{\"id\":4,\"name\":\"tag4\"},{\"id\":5,\"name\":\"tag5\"}]}"))
                .andReturn();

        String foundStr = result.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(foundStr, Map.class);
        int id = (Integer) map.get("id");

        this.mockMvc.perform(get("/api/v1/catalog/offers/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("TestOffer4")))
                .andExpect(content().string(containsString("TestDescription4")))
                .andExpect(content().string(containsString("tag5")));

        this.mockMvc.perform(delete("/api/v1/catalog/offers/" + id))
                .andDo(print())
                .andExpect(status().isNoContent());

        this.mockMvc.perform(get("/api/v1/catalog/offers/" + id))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void updateOffer() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/api/v1/catalog/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"TestOffer4\",\"description\":\"TestDescription4\",\"category\":{\"id\":4,\"name\":\"cat4\"},\"price\":444.44,\"tags\":[{\"id\":4,\"name\":\"tag4\"},{\"id\":5,\"name\":\"tag5\"}]}"))
                .andReturn();

        String foundStr = result.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(foundStr, Map.class);
        int id = (Integer) map.get("id");

        this.mockMvc.perform(put("/api/v1/catalog/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"" + id + "\",\"name\":\"UpdatedTestOffer4\",\"description\":\"UpdatedTestDescription4\",\"category\":{\"id\":1,\"name\":\"cat1\"},\"price\":444.44,\"tags\":[{\"id\":1,\"name\":\"tag1\"},{\"id\":4,\"name\":\"tag4\"},{\"id\":5,\"name\":\"tag5\"}]}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("UpdatedTestOffer4")))
                .andExpect(content().string(containsString("UpdatedTestDescription4")))
                .andExpect(content().string(containsString("tag1")))
                .andExpect(content().string(containsString("cat1")));

        this.mockMvc.perform(delete("/api/v1/catalog/offers/" + id))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateNotExistingOffer() throws Exception {
        this.mockMvc.perform(put("/api/v1/catalog/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"TestOffer4\",\"description\":\"TestDescription4\",\"category\":{\"id\":4,\"name\":\"cat4\"},\"price\":444.44,\"tags\":[{\"id\":4,\"name\":\"tag4\"},{\"id\":5,\"name\":\"tag5\"}]}"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}