package com.example.coll_tab.models;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "_id",
        "firstName",
        "lastName",
        "id",
        "gamesPassword",
        "address",
        "phone",
        "birth"
//        "notes"
})
public class Child {
    @JsonProperty("_id")
    public String _id;
    @JsonProperty("firstName")
    public String firstName;
    @JsonProperty("lastName")
    public String lastName;
    @JsonProperty("id")
    public String id;
    @JsonProperty("gamesPassword")
    public String gamesPassword;
    @JsonProperty("address")
    public String address;
    @JsonProperty("phone")
    public String phone;
    @JsonProperty("birth")
    public String birth;
//    @JsonProperty("notes")
//    public Note notes;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
