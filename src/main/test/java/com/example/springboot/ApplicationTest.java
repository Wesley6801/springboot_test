package com.example.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTest {
    @Autowired
    private MockMvc mockMvc;


    @Test
    void testHistory() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api?post_input_test=testing")).andReturn();
        mockMvc.perform(MockMvcRequestBuilders.get("/History").contentType(MediaType.ALL))
                .andExpect(content().string(containsString("")));
    }

    @Test
    void testDelete() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api?post_input_test=stringToDelete")).andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/delete?post_text=stringToDelete"))
                .andExpect(content().string(containsString("has been deleted")));
        mockMvc.perform(MockMvcRequestBuilders.post("/delete?post_text=stringToDelete").contentType(MediaType.ALL))
                .andExpect(content().string(containsString("does not exist")));
    }

    @Test
    void testSearch() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api?post_input_text=toBeSearched")).andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/api?pst_input_text=notToBeSearched")).andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get("/history").contentType(MediaType.ALL))
            .andExpect(content().string(containsString("toBeSearched")));

        mockMvc.perform(MockMvcRequestBuilders.get("/history").contentType(MediaType.ALL))
                .andExpect(content().string(containsString("notToBeSearched")));

        mockMvc.perform(MockMvcRequestBuilders.post("/api?post_input_text=toBeSearched").contentType(MediaType.ALL))
                .andExpect(content().string(containsString("toBeSearched")));
        mockMvc.perform(MockMvcRequestBuilders.post("/api?post_input_text=toBeSearched").contentType(MediaType.ALL))
                .andExpect(content().string(containsString("notToBeSearched")));
    }


}