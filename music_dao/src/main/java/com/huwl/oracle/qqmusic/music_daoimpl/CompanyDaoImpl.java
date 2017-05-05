package com.huwl.oracle.qqmusic.music_daoimpl;


import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.huwl.oracle.qqmusic.music_dao.CompanyDao;
import com.huwl.oracle.qqmusic.music_model.Company;
import com.huwl.oracle.qqmusic.music_model.Singer;
@Repository("companyDao")
public class CompanyDaoImpl extends BaseDaoImpl<Company> implements CompanyDao {
	{
		setType(Company.class);
	}

	


}
