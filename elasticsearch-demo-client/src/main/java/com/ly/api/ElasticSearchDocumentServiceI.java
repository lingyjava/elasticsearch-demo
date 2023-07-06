package com.ly.api;

import com.ly.dto.UserDTO;

import java.io.IOException;
import java.util.List;

/**
 * @author Ly
 */
public interface ElasticSearchDocumentServiceI {

    void add(UserDTO user) throws IOException;

    public void update(UserDTO user) throws IOException;

    public Boolean exist(UserDTO user) throws IOException;

    public UserDTO get(UserDTO user) throws IOException;

    public void delete(UserDTO user) throws IOException;

    public void bulk(List<UserDTO> userList) throws IOException;

    public List<UserDTO> search(UserDTO user) throws IOException;

}
