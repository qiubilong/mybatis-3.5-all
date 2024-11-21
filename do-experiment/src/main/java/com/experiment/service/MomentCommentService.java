package com.experiment.service;

import com.experiment.mapper.MomentCommentExtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author chenxuegui
 * @since 2024/11/21
 */
@Component
public class MomentCommentService {

    @Autowired
    private MomentCommentExtMapper momentCommentExtMapper;
}
