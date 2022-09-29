<h2 align="center"> 🎓 Java Web Development <br/>(powered by Epam)<br/>Final project: HotelBooking</h2>

<h4> EN: All copyrights to the terms of the project provided below belong to the company 
<a href="https://www.epam.com/" rel="nofollow">EPAM</a><br>
</h4>
<hr align="center">

<h2> 
📑 Content
</h2>
<li><a href="#project-topic">Project topic</a></li>
<li><a href="#db-requirements">Database Requirements</a></li>
<li><a href="#basic-application-requirements">Basic application requirements</a></li>
<li><a href="#min-application-functionality-requirements">Minimum application functionality requirements</a></li>

<h2> 
<a id="user-content-project-topic" class="anchor" aria-hidden="true" href="#project-topic">
<svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true">
<path fill-rule="evenodd" d="M7.775 3.275a.75.75 0 001.06 1.06l1.25-1.25a2 2 0 112.83 2.83l-2.5 2.5a2 2 0 01-2.83 0 .75.75 0 00-1.06 1.06 3.5 3.5 0 004.95 0l2.5-2.5a3.5 3.5 0 00-4.95-4.95l-1.25 1.25zm-4.69 9.64a2 2 0 010-2.83l2.5-2.5a2 2 0 012.83 0 .75.75 0 001.06-1.06 3.5 3.5 0 00-4.95 0l-2.5 2.5a3.5 3.5 0 004.95 4.95l1.25-1.25a.75.75 0 00-1.06-1.06l-1.25 1.25a2 2 0 01-2.83 0z"></path>
</svg></a>
🛒 Project topic
</h2>
<p>
<strong>User</strong> is looking for free rooms, indicating the capacity and the class of apartments, the time of stay.<br> According to the search results, the user adds the selected commands to the booking. User can remove items from the booking or clear it completely. User can log in to an account or register a new one. <br>
Change of languages (RU and EN) is implemented.
<br><br>
<strong>When logged in</strong>, <strong>Client</strong> can, based on the booking, make a reservation. The cancellation option of the reservation is available.<br>
<br>
<strong>Client</strong> has a personal profile in which he can change his personal information or password, upload or change a profile photo and view all their reservations
<br><br>
<strong>Administrator</strong> additionally has the ability to change roles, reservation status, edit information about rooms and add new ones, view all information about users and reservations.
</p>

<h2> 
<a id="user-content-db-requirements" class="anchor" aria-hidden="true" href="#db-requirements">
<svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true">
<path fill-rule="evenodd" d="M7.775 3.275a.75.75 0 001.06 1.06l1.25-1.25a2 2 0 112.83 2.83l-2.5 2.5a2 2 0 01-2.83 0 .75.75 0 00-1.06 1.06 3.5 3.5 0 004.95 0l2.5-2.5a3.5 3.5 0 00-4.95-4.95l-1.25 1.25zm-4.69 9.64a2 2 0 010-2.83l2.5-2.5a2 2 0 012.83 0 .75.75 0 001.06-1.06 3.5 3.5 0 00-4.95 0l-2.5 2.5a3.5 3.5 0 004.95 4.95l1.25-1.25a.75.75 0 00-1.06-1.06l-1.25 1.25a2 2 0 01-2.83 0z"></path>
</svg></a>
📚 Database Requirements
</h2>
<p>
• Information about the subject area should be stored in the database<br>
• If the data in the database is stored in Cyrillic, it is recommended to use utf-8 encoding<br>
• JDBC-only database access technology<br>
• To work with the database in the application, a thread-safe connection pool must be implemented, the use of synchronized and volatile is prohibited.<br>
• When designing a database, it is recommended to use no more than 6-8 tables<br>
</p>

<h2> 
<a id="user-content-basic-application-requirements" class="anchor" aria-hidden="true" href="#basic-application-requirements">
<svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true">
<path fill-rule="evenodd" d="M7.775 3.275a.75.75 0 001.06 1.06l1.25-1.25a2 2 0 112.83 2.83l-2.5 2.5a2 2 0 01-2.83 0 .75.75 0 00-1.06 1.06 3.5 3.5 0 004.95 0l2.5-2.5a3.5 3.5 0 00-4.95-4.95l-1.25 1.25zm-4.69 9.64a2 2 0 010-2.83l2.5-2.5a2 2 0 012.83 0 .75.75 0 001.06-1.06 3.5 3.5 0 00-4.95 0l-2.5 2.5a3.5 3.5 0 004.95 4.95l1.25-1.25a.75.75 0 00-1.06-1.06l-1.25 1.25a2 2 0 01-2.83 0z"></path>
</svg></a>
🗿 Basic application requirements
</h2>
<p>
• Implement the application using Spring Framework and JSP technologies.<br>
• The application architecture must conform to the Layered architecture and MVC patterns. Controller can only be of two kinds: role controller or application controller.<br>
• Application interface must be localized; choice of languages: EN|RU etc.<br>
• The application must correctly handle emerging exceptions, including keeping their log. Use Log4J2/SLF4J as a logger<br>
• Classes and other entities of the application must be properly structured into packages and have a name that reflects their functionality.<br>
• To store user information between requests, use a session.<br>
• Apply filters to intercept and modify request and response objects.<br>
• JSTL tags must be used when implementing JSP pages, scriptlets are not allowed.<br>
• Implement protection against re-execution of the request by pressing F5 and from js injection.<br>
• Viewing "long lists" is desirable to organize in page-by-page mode.<br>
• Validate input data on the client and on the server.<br>
• Documentation for the project must be prepared in accordance with the requirements of javadoc.<br>
• Code formatting must comply with the Java Code Convention.<br>
• When deploying the application, it is allowed to use Maven technology.<br>
• The application must contain JUnit.<br>
• The application must be hosted on a remote git repository.<br>
</p>

<h2> 
<a id="user-content-min-application-functionality-requirements" class="anchor" aria-hidden="true" href="#min-application-functionality-requirements">
<svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true">
<path fill-rule="evenodd" d="M7.775 3.275a.75.75 0 001.06 1.06l1.25-1.25a2 2 0 112.83 2.83l-2.5 2.5a2 2 0 01-2.83 0 .75.75 0 00-1.06 1.06 3.5 3.5 0 004.95 0l2.5-2.5a3.5 3.5 0 00-4.95-4.95l-1.25 1.25zm-4.69 9.64a2 2 0 010-2.83l2.5-2.5a2 2 0 012.83 0 .75.75 0 001.06-1.06 3.5 3.5 0 00-4.95 0l-2.5 2.5a3.5 3.5 0 004.95 4.95l1.25-1.25a.75.75 0 00-1.06-1.06l-1.25 1.25a2 2 0 01-2.83 0z"></path>
</svg></a>
⚙ Minimum application functionality requirements 
</h2>
<p>
• Authorization (sign in) and exit (sign out) to / from the system.<br>
• Registering a user and/or adding a system domain artifact.<br>
• View information<br>
• Removing information (for example: canceling an order, deleting an entity, etc.)<br>
• Adding and modifying information (for example: create and edit a product, create and edit an order, etc.)<br>
</p>
<strong>Author: Yulia Saidak</strong>