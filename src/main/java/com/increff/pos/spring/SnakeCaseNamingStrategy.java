package com.increff.pos.spring;
import org.hibernate.cfg.DefaultNamingStrategy;

public class SnakeCaseNamingStrategy extends DefaultNamingStrategy {
    private static final long serialVersionUID = 1L;

    @Override
    public String classToTableName(String className) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < className.length(); i++) {
            char c = className.charAt(i);
            if (Character.isUpperCase(c)) {
                buf.append("_");
                buf.append(Character.toLowerCase(c));
            } else {
                buf.append(c);
            }
        }
        return buf.toString();
    }
}