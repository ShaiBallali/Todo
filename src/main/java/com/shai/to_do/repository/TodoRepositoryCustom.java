package com.shai.to_do.repository;

public interface TodoRepositoryCustom {
    void updateStateById(Integer id, String state);
    void deleteByCustomId(Integer id);
}
