package ru.timotege.vk.exception;

public class VkUserNotFoundException extends RuntimeException {
    public VkUserNotFoundException() {
        super("User with this id not found.");
    }
}
