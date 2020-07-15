<!DOCTYPE html>
<html lang="en">
<head>
    <title>Guest Registration Card</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body style="font-size: 11px">
    <div class="container">
        <h1>${hotel_name}</h1>
        <table border="1" width="100%" align="center" cellspacing="0" style="border-bottom: none">
            <tr>
                <td border="none" width="25%">
                    <p>Booking ID: ${booking_id}</p>
                </td>
                <td width="50%">
                    <h3 style="text-align:center; font-size:20px">Guest Registration Card</h3>
                </td>
                <td width="25%">
                    <p>No of Visits: </p>
                </td>
            </tr>
        </table>
        <table border="1" width="100%" align="center" cellspacing="0" style="border-bottom: none">
            <tr>
                <td width="75%" align=left>
                    <p><b>Guest Name: </b> ${guest_name}</p>
                </td>
                <td width="25%">
                    <p><b>Purpose of visit:</b></p>
                </td>
            </tr>
        </table>
        <table border="1" width="100%" align="center" cellspacing="0" style="border-bottom: none">
            <tr>
                <td width="75%"></td>

                <td width="25%">
                    <p><b>Arrived from:</b></p>
                </td>
            </tr>
        </table>
        <table border="1" width="100%" align="center" cellspacing="0" style="border-bottom: none">
            <tr>
                <td width="75%"><b>Company:</b><br>
                    <b>Travel Agent: </b>
                </td>

                <td width="25%">
                    <p><b>Next Destination: </b></p>
                </td>
            </tr>
            <tr>
        </table>
        <table border="1" width="100%" align="center" cellspacing="0" style="border-bottom: none">
            <td width="50%"><b>Permanent Address / Business Address:</b>
                <p></p>
                <p></p><br><br><br><br><br><br><br>
                <p></p>
                <p></p>
                <p><b>Telephone no:</b> ${contact_number}</p>
                <p><b>Fax no: </b><br></p>
                <p><b>E-mail: </b>${email}</p>
            </td>
            <td width="25%">
                <p><b>Date of birth: </b><br>
                    ${date_of_birth}</p>
                <hr style="border-color: black">
                <p><b>Nationality: </b><br>
                    ${nationality} </p>
                <hr style="border-color: black">
                <p><b>Wake up Call: </b></p>
                <label class="form-check-label">
                    <input type="checkbox" class="form-check-input" value="">Yes
                </label>
                <label class="form-check-label">
                    <input type="checkbox" class="form-check-input" value="">No
                </label>
                <br><br>
                <p><b>Time: __________</b></p>
            </td>
            <td width="25%">
                <!-- <ul> -->
                <p align="center"><b>Arrival Details</b></p>
                <P> <b>Date:</b> ${arrival_date}</P>

                <P> <b>Time:</b> ${check_in_time}</P>
                <p><b>Flight:</b> </p>
                <hr style="border-color: black">
                <p align="center"><b>Departure Details</b></p>
                <P> <b>Date:</b> ${departure_date}</P>

                <P> <b>Time:</b> </P>
                <p><b>Flight:</b> </p>
                <!-- </ul> -->

            </td>

            </tr>
        </table>
        <table border="1" width="100%" align="center" cellspacing="0" style="border-bottom: none">
            <tr>
                <td width="25%">
                    <p><b>ID/Passport Number: </b></p><br>
                </td>
                <td width="25%">
                    <p><b> Place of issue: </b></p><br>
                </td>
                <td width="25%">
                    <p><b>Date of issue: </b></p><br>
                </td>
                <td width="25%">
                    <p><b>Date of Expiry: </b></p><br>
                </td>
            </tr>
        </table>
        <table border="1" width="100%" align="center" cellspacing="0" style="border-bottom: none">
            <tr>
                <td width="25%">
                    <p><b>Visa Number: </b></p><br>


                </td>
                <td width="25%">
                    <p><b>Place of issue: </b></p><br>
                </td>
                <td width="25%">
                    <p><b> Date of issue: </b></p><br>

                    <b></b>
                </td>
                <td width="25%">
                    <p><b>Date of Expiry: </b></p><br>

                </td>
            </tr>
        </table>
        <table border="1" width="100%" align="center" cellspacing="0" style="border-bottom: none">
            <tr>
                <td width="25%">
                    <p><b>Room No: </b><br>
                        ${room_number}</p>
                </td>
                <td width="25%">
                    <p><b>Room Type: </b><br> ${room_type}</p>
                </td>
                <td width="25%">
                    <p><b>Adults/Children: </b><br> ${no_of_adults} / ${no_of_children}</p>
                </td>
                <td width="25%">
                    <p><b>Room Rate: </b><br> ${room_rate}</p>
                </td>
            </tr>
        </table>
        <table border="1" width="100%" align="center" cellspacing="0" style="border-bottom: none">
            <tr>
                <td width="25%">
                    <p><b>Mode of Payment:</b><br>
                        ${credit_card_number}</p>
                </td>
                <td width="25%">
                    <p><b>Expiry:</b><br>
                        ${card_expiry_date}</p>
                </td>
                <td width="25%">
                    <p><b>Certificate of Registration:</b></p>
                    <div style="width:50%; float:left">

                        <b>Number:</b><br>
                        <b>D.O.I:</b>

                    </div>
                    <div style="width:50%; float:left">

                        <b> P.O.I:</b><br>
                        <b>D.O.E:</b>
                    </div>
                </td>
                <td width="25%">
                    <p><b>POS in India:</b></p>
                    <p>Days:</p>
                </td>
            </tr>
        </table>
        <table border="1" width="100%" align="center" cellspacing="0" style="border-bottom: none">
            <tr>
                <td width="75%">
                    <b>Billing instructions:</b>
                    <p>EP ENTR RS800+ Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean mattis
                        scelerisque pretium</p>


                </td>

                <td width="25%">
                    <p><b>Membership:</b></p>
                    Type:<br>
                    Number:
                </td>
            </tr>
        </table>
        <table border="1" width="100%" align="center" cellspacing="0">
            <tr>
                <td width="100%" style="font-size: 8px">
                    <label class="form-check-label">
                        <input type="checkbox" class="form-check-input" value="" checked>
                    </label>
                    ${terms_and_conditions}
                    <div>
                        <p style="font-size: 11px">Name: ${guest_name}</p>
                        <p style="font-size: 11px">Signature:
                            <img height=20px width=auto src="${signature_url}" />
                        </p>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</body>
</html>