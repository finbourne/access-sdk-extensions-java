package com.finbourne.access.extensions;

import com.finbourne.access.ApiClient;
import com.finbourne.access.api.PoliciesApi;
import com.finbourne.access.model.PolicyResponse;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class ApiFactoryTest {

    private ApiFactory apiFactory;
    private ApiClient apiClient;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp(){
        apiClient = mock(ApiClient.class);
        apiFactory = new ApiFactory(apiClient);
    }

    // UNCOMMENT BELOW TESTS AND MODIFY THEM FOR THE DESIRED SDK - DRIVE EXAMPLES BEING SHOWN HERE
    // General Cases

     @Test
     public void build_ForPoliciesApi_ReturnPoliciesApi(){
         PoliciesApi policiesApi = apiFactory.build(PoliciesApi.class);
         assertThat(policiesApi, instanceOf(PoliciesApi.class));
     }

     @Test
     public void build_ForAnyApi_SetsTheApiFactoryClientAndNotTheDefault(){
         PoliciesApi policiesApi = apiFactory.build(PoliciesApi.class);
         assertThat(policiesApi.getApiClient(), equalTo(apiClient));
     }

     // Singleton Check Cases

     @Test
     public void build_ForSameApiBuiltAgainWithSameFactory_ReturnTheSameSingletonInstanceOfApi(){
         PoliciesApi policiesApi = apiFactory.build(PoliciesApi.class);
         PoliciesApi policiesApiSecond = apiFactory.build(PoliciesApi.class);
         assertThat(policiesApi, sameInstance(policiesApiSecond));
     }

     @Test
     public void build_ForSameApiBuiltWithDifferentFactories_ReturnAUniqueInstanceOfApi(){
         PoliciesApi policiesApi = apiFactory.build(PoliciesApi.class);
         PoliciesApi policiesApiSecond = new ApiFactory(mock(ApiClient.class)).build(PoliciesApi.class);
         assertThat(policiesApi, not(sameInstance(policiesApiSecond)));
     }

     // Error Cases

     @Test
     public void build_ForNonApiPackageClass_ShouldThrowException(){
         thrown.expect(UnsupportedOperationException.class);
         thrown.expectMessage("com.finbourne.access.model.PolicyResponse class is not a supported API class. " +
                 "Supported API classes live in the " + ApiFactory.API_PACKAGE + " package.");
         apiFactory.build(PolicyResponse.class);
     }



}
