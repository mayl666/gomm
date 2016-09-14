package com.gome.upm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.upm.dao.MoMonitorMapper;
import com.gome.upm.domain.MoMonitorAtp;
import com.gome.upm.domain.MoMonitorDragon;
import com.gome.upm.service.MoMonitorService;
@Service
public class MoMonitorServiceImpl implements MoMonitorService {

	@Resource
	private MoMonitorMapper moMonitorMapper;
	
	@Override
	public List<MoMonitorAtp> g3pp_realy_na() {
		return moMonitorMapper.g3pp_realy_na();
	}

	@Override
	public List<MoMonitorAtp> g3pp_realy_dh() {
		return moMonitorMapper.g3pp_realy_dh();
	}

	@Override
	public List<MoMonitorDragon> g3pp_order_pr() {
		return moMonitorMapper.g3pp_order_pr();
	}

	@Override
	public List<MoMonitorDragon> g3pp_order_dh() {
		return moMonitorMapper.g3pp_order_dh();
	}

	@Override
	public List<MoMonitorDragon> g3pp_order_cco() {
		return moMonitorMapper.g3pp_order_cco();
	}

	@Override
	public List<MoMonitorDragon> smi_order_pr() {
		return moMonitorMapper.smi_order_pr();
	}

	@Override
	public List<MoMonitorDragon> order_od() {
		return moMonitorMapper.order_od();
	}

	@Override
	public Integer g3pp_realy_na_count() {
		return moMonitorMapper.g3pp_realy_na_count();
	}

	@Override
	public Integer g3pp_realy_dh_count() {
		return moMonitorMapper.g3pp_realy_dh_count();
	}

	@Override
	public Integer g3pp_order_pr_count() {
		return moMonitorMapper.g3pp_order_pr_count();
	}

	@Override
	public Integer g3pp_order_dh_count() {
		return moMonitorMapper.g3pp_order_dh_count();
	}

	@Override
	public Integer g3pp_order_cco_count() {
		return moMonitorMapper.g3pp_order_cco_count();
	}

	@Override
	public Integer smi_order_pr_count() {
		return moMonitorMapper.smi_order_pr_count();
	}

	@Override
	public Integer order_od_count() {
		return moMonitorMapper.order_od_count();
	}

}
