package com.github.alllef.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HtmlTemplate {
    CLIENT_TOUR_REQUEST("""
            <b>Type: </b>%s<br>
            <b>Price: </b>%d<br>
            <b>Hotel type: </b>%s<br>
            <b>Number of people: </b>%d<br>
            <b>Status: </b>%s<br>
            <b>My discount: </b>%d%%<br>
            <b>Final price: </b>%d <br>
            """),
    ADMIN_USERS_CATALOGUE("""
                        <b>First name: </b>%s<br>
                        <b>Last name: </b>%s<br>
                        <b>Email</b>%s<br>
                        <form id="user-block-request" action="users" method="post">
                       
                       <button type="submit" name="block-user" value ="%d" title="block-user">%s</button>
                        </form>
            """),
    TOUR_FORM("""
               <form id="tour-form" action="tour" method="post">
               <h3>Tour type</h3>
                <select form="tour-form" name="tour-type" id="tour-type">
                    <option value="REST" %s>Rest</option>
                    <option value="EXCURSION" %s>Excursion</option>
                    <option value="SHOPPING" %s>Shopping</option>
                </select>
                        
                <h3>Price</h3>
                <input id="price" name="price" value="%d" type="number"/>
                <h3>People number</h3>
                <input id="people-number" name="people-number" value="%d" type="number"/>
                <h3>Hotel type</h3>
                <select form="tour-form" name="hotel-type" id="hotel-type">
                    <option value="HOSTEL" %s>Hostel</option>
                    <option value="ALL_INCLUSIVE" %s>All inclusive</option>
                </select>
                <h3>Max discount</h3>
                <input id="max-discount" name="max-discount" value="%d" type="number"/>
                <p><input name="burning" type="radio" value ="true" %s>Burning</p>
                <button type="submit" name="save" value="%d" title="Save">Save</button>
            </form>
            """),
    ADMIN_TOUR_CATALOGUE("""
            <b>Type:</b> %s<br>
            <b>Price:</b> %d<br>
            <b>Hotel type:</b> %s<br>
            <b>Number of people:</b> %d<br>
            <form id="order" action="tour" method="get">
                       <button type="submit" name="change-info" value="%d" title="change-info">Change info</button>
                        </form>
                        
            <form id="delete-form" action="tour-catalogue" method="post">
            <button type="submit" name="delete" value="%d" title="delete">Delete</button>
                        </form>
            """),
    CLIENT_TOUR_CATALOGUE("""
            <b>Type:</b> %s<br>
            <b>Price:</b> %d<br>
            <b>Hotel type:</b> %s<br>
            <b>Number of people:</b> %d<br>
            <form id="order" action="catalogue" method="post">
                       <button type="submit" name="order" value="%d" title="Order">Order</button>
                        </form>
            """),
    MANAGER_TOUR_CATALOGUE("""
            <b>Type:</b> %s<br>
            <b>Price:</b> %d<br>
            <b>Hotel type:</b> %s<br>
            <b>Number of people:</b> %d<br>
            <form id="manager-update" action="/managing/tours" method="post">
                        <h3>Max discount</h3>
                           <input id="discount" name="max-discount" value="%d" type="number"/>
                       <p><input name="burning" type="radio" value ="true" %s>Burning</p>
                       <button type="submit" name="update-tour" value ="%d" title="update-tour">Update tour</button>
                        </form>
            """),
    MANAGER_TOUR_REQUEST("""
                        <b>Type: </b>%s<br>
                        <b>Price: </b>%d<br>
                        <b>Hotel type: </b>%s<br>
                        <b>Number of people: </b>%d<br>
                        <b>Status: </b>%s<br>
                        <form id="manager-update-tour-request" action="tour-requests" method="post">
                        <h3>Discount</h3>
                           <input id="discount" name="discount" value="%d" type="number"/>
                           <h3>Status</h3>
                           <select form="manager-update-tour-request" name="status" id="status">
                               <option value="REGISTERED" %s>Registered</option>
                               <option value="PAID" %s>Paid</option>
                               <option value = "CANCELED" %s>Canceled</option>
                           </select>
                       
                       <button type="submit" name="update-tour-request" value ="%d" title="update-tour-request">Update request</button>
                        </form>
                        <b>Final price: </b>%d <br>
            """);

    private String template;

}
