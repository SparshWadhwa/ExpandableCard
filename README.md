ExpandableCard
==============

An expandable list view that displays one set of data collapsed, one set expanded

To use:
The shortList parameter in the constructor of ExpandableCard will be displayed when it is collapsed
The longList parameter in the constructor will be displayed when it is expanded

To change the header, change list_group.xml and modify getGroupView. Currently it just sets a textView value, 
but it can be modified to display anything you can put in a layout xml

Similarly, to change the data fields, change list_item.xml and getChildView.

ListGroup and ListItem can be overwritten to hold custom data as well.

To change the icon used for the indicator found on the last row, modify getIndicatorDrawable

