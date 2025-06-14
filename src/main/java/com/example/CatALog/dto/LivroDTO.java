package com.example.CatALog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LivroDTO {
    public String id;
    public VolumeInfo volumeInfo;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VolumeInfo {
        public String title;
        public List<String> authors;
        public String publisher;
        public String publishedDate;
        public String description;
        public List<IndustryIdentifier> industryIdentifiers;
        public ImageLinks imageLinks;
        public int pageCount;
        public List<String> categories;

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class IndustryIdentifier {
        public String type;
        public String identifier;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ImageLinks {
        public String thumbnail;
    }
}
