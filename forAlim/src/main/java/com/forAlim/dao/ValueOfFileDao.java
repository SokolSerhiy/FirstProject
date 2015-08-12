package com.forAlim.dao;

import com.forAlim.entity.ValueOfFile;

public interface ValueOfFileDao extends GeneralDao<ValueOfFile, Integer>{
	
	ValueOfFile findByIdSavedFile(int savedFileId);
}
