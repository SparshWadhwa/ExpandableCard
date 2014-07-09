package com.example.testproject;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpandableCard extends BaseExpandableListAdapter
{
	private static final int SHORT_INDEX = 0;
	private static final int LONG_INDEX = 1;
	protected static final int COLLAPSING = -1;

	private Context m_Context;
	private ArrayList<ListItem>[] m_ListArray;
	private int m_CurrentIndex = SHORT_INDEX;
	private ListGroup m_Header;
	private Drawable m_Indicator;
	private ExpandableListView m_ExpListView;

	@SuppressWarnings("unchecked")
	public ExpandableCard(Context context, String headerTitle, ArrayList<ListItem> shortList, ArrayList<ListItem> longList)
	{
		this.m_Context = context;
		m_Header = new ListGroup(headerTitle);
		m_ListArray = new ArrayList[] { shortList, longList };
		ListItem indicatorRow = new ListItem("blank");;
		m_ListArray[SHORT_INDEX].add(indicatorRow);
		m_ListArray[LONG_INDEX].add(indicatorRow);
		
		getIndicatorDrawable();
		
		setListeners();
		
		m_ExpListView.setAdapter(this);
		m_ExpListView.expandGroup(0);
	}
	
	private void setListeners()
	{
		m_ExpListView = new ExpandableListView(m_Context);
		m_ExpListView.setGroupIndicator(null);
		m_ExpListView.setOnGroupClickListener(new OnGroupClickListener()
		{
			@Override
			public boolean onGroupClick(ExpandableListView arg0, View arg1, int arg2, long arg3)
			{
				if (m_CurrentIndex == ExpandableCard.SHORT_INDEX)
				{
					m_CurrentIndex = ExpandableCard.COLLAPSING;
					m_ExpListView.collapseGroup(0);
					m_CurrentIndex = ExpandableCard.LONG_INDEX;
					m_ExpListView.expandGroup(0);
				}
				else if (m_CurrentIndex == ExpandableCard.LONG_INDEX)
				{
					m_CurrentIndex = ExpandableCard.COLLAPSING;
					m_ExpListView.collapseGroup(0);
					m_CurrentIndex = ExpandableCard.SHORT_INDEX;
					m_ExpListView.expandGroup(0);
				}
				return false;
			}
		});
		
		m_ExpListView.setOnChildClickListener(new OnChildClickListener()
		{	
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
			{
				if (m_CurrentIndex == ExpandableCard.SHORT_INDEX)
				{
					m_CurrentIndex = ExpandableCard.COLLAPSING;
					m_ExpListView.collapseGroup(0);
					m_CurrentIndex = ExpandableCard.LONG_INDEX;
					m_ExpListView.expandGroup(0);
				}
				else if (m_CurrentIndex == ExpandableCard.LONG_INDEX)
				{
					m_CurrentIndex = ExpandableCard.COLLAPSING;
					m_ExpListView.collapseGroup(0);
					m_CurrentIndex = ExpandableCard.SHORT_INDEX;
					m_ExpListView.expandGroup(0);
				}
				return false;
			}
		});
	}
	
	private void getIndicatorDrawable()
	{
		// This can be replaced with any drawable resource
		
		// obtain expandableListViewStyle from theme
		TypedArray expandableListViewStyle = m_Context.getTheme().obtainStyledAttributes(new int[] { android.R.attr.expandableListViewStyle });
		// obtain attr from style
		TypedArray groupIndicator = m_Context.getTheme().obtainStyledAttributes(expandableListViewStyle.getResourceId(0, 0),
				new int[] { android.R.attr.groupIndicator });
		
		m_Indicator = groupIndicator.getDrawable(0);
		
		expandableListViewStyle.recycle();
		groupIndicator.recycle();
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View view, ViewGroup parent)
	{
		// If out of bounds return null
		if (childPosition >= m_ListArray[m_CurrentIndex].size())
			return null;
		
		// Get the list item
		ListItem listItem = ((ListItem) getChild(groupPosition, childPosition));
		
		// It seems to reuse the same view, so if it doesn't hold our custom components, make it do so
		if (view == null || view instanceof ImageView)
		{
			LayoutInflater infalInflater = (LayoutInflater) this.m_Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = infalInflater.inflate(R.layout.list_item, null);
		}
		
		// If its the last index, change it to the indicator row
		if (childPosition == m_ListArray[m_CurrentIndex].size() - 1)
		{
			// This can be changed to any other view
			ImageView indicatorRow = new ImageView(m_Context);
			if (m_CurrentIndex == SHORT_INDEX)
			{
				indicatorRow.setImageDrawable(m_Indicator);
				indicatorRow.setScaleY(1);
			}
			else
			{
				indicatorRow.setImageDrawable(m_Indicator);
				indicatorRow.setScaleY(-1);
			}
			indicatorRow.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			return indicatorRow;
		}
		else // Set the text and other components of a normal row
		{
			TextView textView = (TextView) view.findViewById(R.id.textDisplay);
			textView.setText(listItem.toString());
			return view;
		}
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent)
	{
		String headerTitle = ((ListGroup) getGroup(groupPosition)).toString();
		if (view == null)
		{
			LayoutInflater inflater = (LayoutInflater) this.m_Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.list_group, null);
		}

		TextView headerView = (TextView) view.findViewById(R.id.headerTextView);
		headerView.setTypeface(null, Typeface.BOLD);
		headerView.setText(headerTitle);

		return view;
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon)
	{
		return m_ListArray[m_CurrentIndex].get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition)
	{
		return childPosition;
	}

	@Override
	public int getChildrenCount(int groupPosition)
	{
		return m_ListArray[m_CurrentIndex].size();
	}

	@Override
	public Object getGroup(int groupPosition)
	{
		return m_Header;
	}

	@Override
	public int getGroupCount()
	{
		return 1;
	}

	@Override
	public long getGroupId(int groupPosition)
	{
		return groupPosition;
	}

	@Override
	public boolean hasStableIds()
	{
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition)
	{
		return true;
	}

	public View getView()
	{
		return m_ExpListView;
	}
}