/**
 * 添加到购物车的js文件
 */

/*
 * Returns an new XMLHttpRequest object, or false if the browser
 * doesn't support it
 */
function isLogin(user_id){
	if(user_id == 0){
		return false;
	}
	return true;
}

function newXMLHttpRequest() {

  var xmlreq = false;

  // Create XMLHttpRequest object in non-Microsoft browsers
  if (window.XMLHttpRequest) {
    xmlreq = new XMLHttpRequest();

  } else if (window.ActiveXObject) {

    try {
      // Try to create XMLHttpRequest in later versions
      // of Internet Explorer

      xmlreq = new ActiveXObject("Msxml2.XMLHTTP");
      
    } catch (e1) {

      // Failed to create required ActiveXObject
      
      try {
        // Try version supported by older versions
        // of Internet Explorer
      
        xmlreq = new ActiveXObject("Microsoft.XMLHTTP");

      } catch (e2) {

        // Unable to create an XMLHttpRequest by any means
        xmlreq = false;
      }
    }
  }

return xmlreq;
}

 /*
	* Returns a function that waits for the specified XMLHttpRequest
	* to complete, then passes it XML response to the given handler function.
  * req - The XMLHttpRequest whose state is changing
  * responseXmlHandler - Function to pass the XML response to
  */
 function getReadyStateHandler(req, responseXmlHandler) {

   // Return an anonymous function that listens to the XMLHttpRequest instance
   return function () {

     // If the request's status is "complete"
     if (req.readyState == 4) {
       
       // Check that we received a successful response from the server
       if (req.status == 200) {

         // Pass the XML payload of the response to the handler function.
         responseXmlHandler(req.responseXML);

       } else {

         // An HTTP problem has occurred
         alert("HTTP error "+req.status+": "+req.statusText);
       }
     }
   }
 }

 function add_to_cart(product_id) {
	 //var user_id = '${userid}';
	 alert('${userid}');
	/* if(user_id == null || user_id == ""){
		 alert("Please Login first!");
	 }
	 else{
		 var req = newXMLHttpRequest();
		 //req.onreadystatechange = getReadyStateHandler(req, updateCart);
		 
		 req.open("POST", "/Eshopper/AjaxAddToCartServlet", true);
		 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		 req.send("user_id=" + user_id + "&product_id=" + product_id);
		 alert("OK!")
	 }*/
	 
}