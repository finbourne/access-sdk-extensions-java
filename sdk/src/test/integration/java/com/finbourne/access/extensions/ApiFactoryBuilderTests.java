package com.finbourne.access.extensions;

import com.finbourne.access.ApiException;
import com.finbourne.access.api.ApplicationMetadataApi;
import com.finbourne.access.model.ResourceListOfAccessControlledResource;
import com.finbourne.access.extensions.auth.FinbourneTokenException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

 public class ApiFactoryBuilderTests {

     @Rule
     public ExpectedException thrown = ExpectedException.none();

     @Test
     public void build_WithExistingConfigurationFile_ShouldReturnFactory() throws ApiException, ApiConfigurationException, FinbourneTokenException {
         ApiFactory apiFactory = ApiFactoryBuilder.build(CredentialsSource.credentialsFile);
         assertThat(apiFactory, is(notNullValue()));
         assertThatFactoryBuiltApiCanMakeApiCalls(apiFactory);
     }

     private static void assertThatFactoryBuiltApiCanMakeApiCalls(ApiFactory apiFactory) throws ApiException {
         ApplicationMetadataApi applicationMetadataApi = apiFactory.build(ApplicationMetadataApi.class);
         ResourceListOfAccessControlledResource listOfAccessControlledResource = applicationMetadataApi.listAccessControlledResources();
         assertThat("app metadata API created by factory should return list of resources"
                 , listOfAccessControlledResource, is(notNullValue()));
         assertThat("resource list contents types returned by the app metadata API should not be empty",
                 listOfAccessControlledResource.getValues(), not(empty()));
     }

 }
