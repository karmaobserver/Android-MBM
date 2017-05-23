package com.mbm.ftn.mbm.database;

import java.util.List;

/**
 * Created by Makso on 5/23/2017.
 */

public interface Crud {
    public int create(Object item);
    public int update(Object item);
    public int delete(Object item);
    public Object findById(int id);
    public List<?> findAll();
}
