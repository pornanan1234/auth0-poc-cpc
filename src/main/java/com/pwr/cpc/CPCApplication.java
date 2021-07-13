package com.pwr.cpc;

import com.pwr.cpc.Model.CPC;

import com.pwr.cpc.Model.PDS;
import com.pwr.cpc.services.CPCservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

@SpringBootApplication
public class CPCApplication {

	@Autowired
	private CPCservice cPCservice;

	public static void main(String[] args) {
		SpringApplication.run(CPCApplication.class, args);
	}


	@RestController
	public class RequestCotroller {

		@PreAuthorize("hasAuthority('SCOPE_pwrscope')")
		@GetMapping("/Catalog")
		public List<CPC> Catalog(JwtAuthenticationToken principal, @RequestParam String customerGroup) {

			List<CPC> CPCs = cPCservice.getAllCPCs();

			List<CPC> resultCPCs = new ArrayList<CPC>();

			for (CPC  cpc : CPCs) {
				if (cpc.getCustomergroup().equals(customerGroup)){

					resultCPCs.add(cpc);
				}

			}


			// New for PDS


			Map<String,String> results =new HashMap<String,String>();
			List<PDS> PDSs =new ArrayList<PDS>();
			// Add the JWT to the RestTemplate headers
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer " + (principal.getToken().getTokenValue()));
			HttpEntity request = new HttpEntity(headers);

			if (!customerGroup.isEmpty() ) {
			//	String requestURL = new String("https://pds-okta.azurewebsites.net/Price?customerGroup=" + customerGroup.toString());
				String requestURL = new String("http://localhost:3001/Price?customerGroup=" + customerGroup.toString());

				// Make the actual HTTP GET request
				RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<List<PDS>> response = restTemplate.exchange(
						requestURL,
						HttpMethod.GET,
						request,
						new ParameterizedTypeReference<List<PDS>>() {
						}
				);


//        String result = response.getBody();
				PDSs = response.getBody();

			}



			//// end pDS


			for (CPC cpc:resultCPCs){
				for (PDS pds: PDSs ) {
					if(cpc.getPlant().equals(pds.getPlant())){
						if(cpc.getProduct().equals(pds.getProduct())){
							if(!pds.getPrice().isEmpty()) {
								cpc.setPrice(pds.getPrice());
							}
						}
					}

				}

			}





			return resultCPCs;
		}



	}

}
