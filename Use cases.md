

**To make reading less tedious, unless specified otherwise, "click" mean left-click.**
Letters (a, b, c, etc.) are variations on one step/procedure. They are disjoint events.


Use case: Panning
1) User clicks (and holds) on map.
2) User drags mouse.
3) User releases mouse button.

Use case: Zooming
a) Zooming in:
    1) User scrolls away from his/herself.
b) Zooming out:
    1) User scrolls towards his/herself.

Use case: Resizing
1) User clicks on border of window
2) User drags border to desired place.
3) User releases mouse button.

Use case: Getting information about a point on a map
1) User clicks on a point.
2) System displays name of closest address and coordinates.

Use case: Selection of starting and ending locations (variations a, b, and c)
1) User right-clicks on a point on the map.
2)
    a) User selects dialog option "Directions from here." This is marked as the starting point.
    b) User selects dialog option "Directions to here." This is marked as the ending point.
    c) User selects dialog option "Drive to destination." This is marked as the ending point.
3) User clicks on another point.
    a) This is selected as the ending point.
    b) This is selected as the starting point.
    c) Current location is selected as starting point.


Use case: Getting directions
1) User selects starting and ending points.
2) Directions are calculated, route is highlighted, and all other pertinent info is shown to user.


Use case: Activating "drive there" mode
1) User selects starting and ending points.
2) Directions are calculated and shown to user.
3) User presses "drive there" button.
4) "Drive there" mode starts.

Use case: production of "off course" directions
1) User deviates from pre-calculated route
2) System shows "off course indication"
3) Route is recalculated
4) User is notified that a new route has been calculated.
