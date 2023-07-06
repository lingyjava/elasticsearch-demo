package com.ly.web;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.ly.api.ElasticSearchDocumentServiceI;
import com.ly.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Ly
 */
@RestController
@RequestMapping("/es")
public class ElasticSearchController {

    @Autowired
    private ElasticSearchDocumentServiceI elasticSearchDocumentService;

    @PostMapping("/user/add")
    public Response addUser(@RequestBody UserDTO user) throws Exception {
        elasticSearchDocumentService.add(user);
        return Response.buildSuccess();
    }

    @PostMapping("/user/update")
    public Response updateUser(@RequestBody UserDTO user) throws Exception {
        elasticSearchDocumentService.update(user);
        return Response.buildSuccess();
    }

    @GetMapping("/user/exist")
    public Response existUser(@RequestBody UserDTO user) throws Exception {
        return SingleResponse.of(elasticSearchDocumentService.exist(user));
    }

    @GetMapping("/user/get")
    public Response getUser(@RequestBody UserDTO user) throws Exception {
        return SingleResponse.of(elasticSearchDocumentService.get(user));
    }

    @PostMapping("/user/delete")
    public Response deleteUser(@RequestBody UserDTO user) throws Exception {
        elasticSearchDocumentService.delete(user);
        return Response.buildSuccess();
    }

    /**
     * test input param :
     * [
     *     {
     *       "userId": 2,
     *       "username": "lc",
     *       "age": 18
     *     },
     *     {
     *       "userId": 3,
     *       "username": "wfw",
     *       "age": 24
     *     }
     *   ]
     * */
    @PostMapping("/user/bulk")
    public Response bulkUser(@RequestBody List<UserDTO> list) throws Exception {
        elasticSearchDocumentService.bulk(list);
        return Response.buildSuccess();
    }

    @GetMapping("/user/search")
    public Response searchUser(@RequestBody UserDTO user) throws Exception {
        return MultiResponse.of(elasticSearchDocumentService.search(user));
    }
}
