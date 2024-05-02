import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.validation.BindingResultUtils.getBindingResult;

import java.beans.PropertyEditor;
import java.util.*;

import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import spring2024.cs472.hotelwebsite.controllers.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spring2024.cs472.hotelwebsite.*;
import spring2024.cs472.hotelwebsite.entities.Admin;
import spring2024.cs472.hotelwebsite.services.AccountService;

public class JUnitTests {
    @Test

    public void testAdminEdit() {
        AccountService accountService = new AccountService();
        AdminAccountCrudController adminAccountCrudController = new AdminAccountCrudController(accountService);
        adminAccountCrudController.updateAdmin(1, accountService.getAllAdmins().get(1), Objects.requireNonNull(getBindingResult()));


    }

    private BindingResult getBindingResult() {
        return new BindingResult() {
            @Override
            public String getObjectName() {
                return "";
            }

            @Override
            public void reject(String errorCode, Object[] errorArgs, String defaultMessage) {

            }

            @Override
            public void rejectValue(String field, String errorCode, Object[] errorArgs, String defaultMessage) {

            }

            @Override
            public List<ObjectError> getGlobalErrors() {
                return List.of();
            }

            @Override
            public List<FieldError> getFieldErrors() {
                return List.of();
            }

            @Override
            public Object getFieldValue(String field) {
                return null;
            }

            @Override
            public String toString() {
                return "";
            }

            @Override
            public Object getTarget() {
                return null;
            }

            @Override
            public Map<String, Object> getModel() {
                return Map.of();
            }

            @Override
            public Object getRawFieldValue(String field) {
                return null;
            }

            @Override
            public PropertyEditor findEditor(String field, Class<?> valueType) {
                return null;
            }

            @Override
            public PropertyEditorRegistry getPropertyEditorRegistry() {
                return null;
            }

            @Override
            public String[] resolveMessageCodes(String errorCode) {
                return new String[0];
            }

            @Override
            public String[] resolveMessageCodes(String errorCode, String field) {
                return new String[0];
            }

            @Override
            public void addError(ObjectError error) {

            }
        };
    }
}
