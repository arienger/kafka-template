package eg.kafka.template.rest;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The EG Apps platform automatically configures security for any request under the /api/** urls.
 * Those urls must be accessed with an EG Apps token created by the logon service.
 * If the eg-apps-token header is provided with a valid token, the platform will set up the security context with tenant (domain) and username for later use
 * with
 */
@RestController
@RequestMapping("/api/articles")
@Slf4j
public class ArticleController {
    @GetMapping(path = "/{articleNumber}")
    public ResponseEntity<JsonNode> lookupOneArticle(@PathVariable String articleNumber){

        //Could lookup the article from dao or something
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
