package br.com.yoshicatsu.exerciciosspringboot.models.enums;

public enum ResponseStatusEnum {
    SUCCESS("success"), ERROR("error"), INFO("info"), WARNING("warning");

    private final String description;

    ResponseStatusEnum(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
    public static boolean isSuccess(String description){
        return description.equalsIgnoreCase(ResponseStatusEnum.SUCCESS.description);
    }
}
