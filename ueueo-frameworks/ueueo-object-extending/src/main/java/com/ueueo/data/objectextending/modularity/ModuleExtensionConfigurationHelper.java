package com.ueueo.data.objectextending.modularity;

import com.ueueo.data.objectextending.ObjectExtensionManager;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.List;

public class ModuleExtensionConfigurationHelper {

    private static final Object SYNC_LOCK = new Object();

    public static void applyEntityConfigurationToEntity(
            String moduleName,
            String entityName,
            Class<?> entityType) {
        synchronized (SYNC_LOCK) {
            for (ExtensionPropertyConfiguration propertyConfig : getPropertyConfigurations(moduleName, entityName)) {
                if (propertyConfig.getEntity().isAvailable() && entityType != null) {
                    applyPropertyConfigurationToTypes(propertyConfig, new Class[]{entityType});
                }
            }
        }
    }

    public static void applyEntityConfigurationToApi(
            String moduleName,
            String objectName,
            Class<?>[] getApiTypes,
            Class<?>[] createApiTypes,
            Class<?>[] updateApiTypes) {
        synchronized (SYNC_LOCK) {
            for (ExtensionPropertyConfiguration propertyConfig : getPropertyConfigurations(moduleName, objectName)) {
                if (!propertyConfig.isAvailableToClients()) {
                    continue;
                }

                if (propertyConfig.getApi().getOnGet().isAvailable() &&
                        getApiTypes != null) {
                    applyPropertyConfigurationToTypes(propertyConfig, getApiTypes);
                }

                if (propertyConfig.getApi().getOnCreate().isAvailable() &&
                        createApiTypes != null) {
                    applyPropertyConfigurationToTypes(propertyConfig, createApiTypes);
                }

                if (propertyConfig.getApi().getOnUpdate().isAvailable() &&
                        updateApiTypes != null) {
                    applyPropertyConfigurationToTypes(propertyConfig, updateApiTypes);
                }
            }
        }
    }

    public static void applyEntityConfigurationToUi(
            String moduleName,
            String entityName,
            Class<?>[] createFormTypes,
            Class<?>[] editFormTypes) {
        synchronized (SYNC_LOCK) {
            for (ExtensionPropertyConfiguration propertyConfig : getPropertyConfigurations(moduleName, entityName)) {
                if (!propertyConfig.isAvailableToClients()) {
                    continue;
                }

                if (propertyConfig.getUi().getOnCreateForm().isVisible() &&
                        createFormTypes != null) {
                    applyPropertyConfigurationToTypes(propertyConfig, createFormTypes);
                }

                if (propertyConfig.getUi().getOnEditForm().isVisible() &&
                        editFormTypes != null) {
                    applyPropertyConfigurationToTypes(propertyConfig, editFormTypes);
                }
            }
        }
    }

    public static void applyEntityConfigurations(
            String moduleName,
            String entityName,
            Class<?> entityType,
            Class<?>[] createFormTypes,
            Class<?>[] editFormTypes,
            Class<?>[] getApiTypes,
            Class<?>[] createApiTypes,
            Class<?>[] updateApiTypes) {
        synchronized (SYNC_LOCK) {
            if (entityType != null) {
                applyEntityConfigurationToEntity(
                        moduleName,
                        entityName,
                        entityType
                );
            }

            applyEntityConfigurationToApi(
                    moduleName,
                    entityName,
                    getApiTypes,
                    createApiTypes,
                    updateApiTypes
            );

            applyEntityConfigurationToUi(
                    moduleName,
                    entityName,
                    createFormTypes,
                    editFormTypes
            );
        }
    }

    @NonNull
    public static List<ExtensionPropertyConfiguration> getPropertyConfigurations(
            String moduleName,
            String entityName) {
        synchronized (SYNC_LOCK) {
            ModuleExtensionConfiguration moduleConfig = ObjectExtensionManager.Instance.getModules().get(moduleName);
            if (moduleConfig == null) {
                return Collections.emptyList();
            }

            EntityExtensionConfiguration objectConfig = moduleConfig.getEntities().get(entityName);
            if (objectConfig == null) {
                return Collections.emptyList();
            }

            return objectConfig.getProperties();
        }
    }

    public static void applyPropertyConfigurationToTypes(
            ExtensionPropertyConfiguration propertyConfig,
            Class<?>[] types) {
        synchronized (SYNC_LOCK) {
            ObjectExtensionManager.Instance
                    .addOrUpdateProperty(
                            types,
                            propertyConfig.getType(),
                            propertyConfig.getName(),
                            property ->
                            {
                                property.getAttributes().clear();
                                property.getAttributes().addAll(propertyConfig.getAttributes());
                                property.setDisplayName(propertyConfig.getDisplayName());
                                property.getValidators().addAll(propertyConfig.getValidators());
                                property.setDefaultValue(propertyConfig.getDefaultValue());
                                property.setDefaultValueFactory(propertyConfig.getDefaultValueFactory());
                                property.setLookup(propertyConfig.getUi().getLookup());
                            }
                    );
        }
    }
}
