package com.example.testproject;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment
{
	private ExpandableCard expandableCard;

	public MainFragment()
	{
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);		
		
		ArrayList<ListItem> primary = new ArrayList<ListItem>();
		primary.add(new ListItem("A"));
		primary.add(new ListItem("B"));
		ArrayList<ListItem> secondary = new ArrayList<ListItem>();
		secondary.add(new ListItem("A"));
		secondary.add(new ListItem("B"));
		secondary.add(new ListItem("C"));
		secondary.add(new ListItem("D"));
		secondary.add(new ListItem("E"));
		expandableCard = new ExpandableCard(getActivity(), "Header", primary, secondary);
		container.addView(expandableCard.getView());
		
		return rootView;
	}
}
