package com.bkalika.socialnetwork.dto;

/**
 * @author @bkalika
 */
public class ErrorDto {
    private Long id;
    private String title;
    private byte[] content;

    public ErrorDto() {
    }

    public ErrorDto(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
