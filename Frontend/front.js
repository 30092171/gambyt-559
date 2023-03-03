const baseURL = 'http://127.0.0.1:8080/api/v1'; // This needs to be updated and set if changed
const userID = 123;

window.onload = function() {
  getTickets();
}

function displayJson(jsonData) {
  // Get a reference to the element where you want to display the tickets
  const ticketsContainer = document.getElementById('tickets-container');

  // Parse the JSON data and get the tickets object
  const data = JSON.parse(jsonData);
  const tickets = data;


  // Loop through the tickets object and generate HTML code for each ticket
  for (const ticketId in tickets) {
    const ticket = tickets[ticketId];
    var priority_value;
    var status_value;
    if (ticket.priority == 0) {
      priority_value = "Low";
    } else if (ticket.priority == 1) {
      priority_value = "Medium";
    } else if (tickets.priority == 2) {
      priority_value = "High";
    }
    if (ticket.status == 0) {
      status_value = "To-Do";
    } else if (ticket.status == 1) {
      status_value = "In-Progress";
    } else if (ticket.status == 2) {
      status_value = "Done";
    }


    const html = `
      <div class="ticket" id="${ticketId}">
        <div class="ticket-title">${ticket.name}</div>
        <div class="ticket-description">${ticket.description}</div>
        <div class="ticket-info">
          <div class="date"><strong>Date:</strong> ${ticket.dateAssigned}</div>
          <div class="priority"><strong>Priority:</strong> ${priority_value}</div>
        </div>
        <div class="ticket-info">
          <div class="assignee"><strong>Assignee:</strong> ${ticket.assigneeName}</div>
          <div class="status"><strong>Status:</strong> ${status_value}</div>
        </div>
        <div class="ticket-buttons">
          <button class="edit" id="edit">Edit</button>
          <button class="delete deleteTicket" type="button" id="deleteTicket">Delete</button>
        </div>
        <div class="ticket-buttons">
          <button class="subscribe" id="subscribe">Subscribe</button>
          <button class="claim" id="claim">Claim</button>
        </div>
      </div>
    `;
    
    // Append the generated HTML code to the tickets container
    ticketsContainer.innerHTML += html;
  }
}

function clearTickets() {
  const ticketsContainer = document.getElementById('tickets-container');
  ticketsContainer.innerHTML = '';
}

function updateDynamicButtons() {
  subscribe = document.getElementsByClassName("subscribe");
  unsubscribe = document.getElementsByClassName("unsubscribe");
  del = document.getElementsByClassName("delete deleteTicket");
  claim = document.getElementsByClassName("claim");
  edit = document.getElementsByClassName("edit");


  for (var i = 0; i < subscribe.length; i++) {
    subscribe[i].addEventListener('click', subTicket);
  }
  // for (var i = 0; i < unsubscribe.length; i++) {
  //   unsubscribe[i].addEventListener('click', unsubTicket);
  // }
  for (var i = 0; i < del.length; i++) {
    del[i].addEventListener('click', deleteTicket);
  }
  for (var i = 0; i < claim.length; i++) {
    claim[i].addEventListener('click', claimTicket);
  }

  for (var i = 0; i < edit.length; i++) {
    edit[i].addEventListener('click', displayEdit);
  }
}



// Get the HTML elements to show/hide
const loginSection = document.getElementById("login-section");
const editTicketSection = document.getElementById("edit-ticket");
const createTicketSection = document.getElementById("create-ticket");
const viewTicketsSection = document.getElementById("view-tickets");
const inboxSection = document.getElementById("inbox");

// Get the menu items
const createTicketLink = document.querySelector("a[href='#create-ticket']");
const viewTicketsLink = document.querySelector("a[href='#view-tickets']");
const inboxLink = document.querySelector("a[href='#inbox']");

// Hide all sections except for the Create a Ticket section initially
loginSection.style.display = "block";
createTicketSection.style.display = "none";
editTicketSection.style.display = "none";
viewTicketsSection.style.display = "none";
inboxSection.style.display = "none";

// Attach click event listeners to switch scenes
createTicketLink.addEventListener("click", function(event) {
  event.preventDefault();
  editTicketSection.style.display = "none";
  createTicketSection.style.display = "block";
  viewTicketsSection.style.display = "none";
  inboxSection.style.display = "none";
});

viewTicketsLink.addEventListener("click", function(event) {
  event.preventDefault();
  editTicketSection.style.display = "none";
  createTicketSection.style.display = "none";
  viewTicketsSection.style.display = "block";
  inboxSection.style.display = "none";
});

inboxLink.addEventListener("click", function(event) {
  event.preventDefault();
  editTicketSection.style.display = "none";
  createTicketSection.style.display = "none";
  viewTicketsSection.style.display = "none";
  inboxSection.style.display = "block";
});

function displayEdit(event) {
  event.preventDefault();
  // Display the edit screen
  editTicketSection.style.display = "block";
  createTicketSection.style.display = "none";
  viewTicketsSection.style.display = "none";
  inboxSection.style.display = "none";
  // Prepopulate the information from the ticket
}


document.addEventListener('DOMContentLoaded', function() {
  console.log("DOM loaded");

  // const jsonData = '{"tickets": {"100": {"name": "Super-Ticket","assignee": 0,"status": 0,"subscribers": ["0", "10", "2", "3"],"description": "Hello World!","date_assigned": "2023-02-15","priority": 0},"618": {"name": "Super Lame Ticket","assignee": 10,"status": 2,"subscribers": [],"description": "This ticket sucks","date_assigned": "2023-02-04","priority": 2}},"inbox": {"0": ["Hello", "World!", "Boo"],"1": ["Leave me Here", "F-Society"]}}';
  // displayJson(jsonData);


  // Get the elements for REST API calls
  const myInbox = document.getElementById("my-inbox");
  const allTickets = document.getElementById("all-tickets");
  const createNewTicket = document.getElementById("submitTicket");
  const updateTicket = document.getElementById("editTicket");
  const submitLogin = document.getElementById("submitLogin");

  updateDynamicButtons();



  // Add listeners
  // myInbox.addEventListener('click', getInbox);
  allTickets.addEventListener('click', getTickets);
  createNewTicket.addEventListener('click', postTicket);
  updateTicket.addEventListener("click", putTicket);
  submitLogin.addEventListener('click', attemptLogin);

});


// Functions

// function getInbox(event) {
//   const path = '/inbox/${userID}';
//   const url = baseURL + path;

//   fetch(url)
//     .then(response => response.json())
//     .then(data => console.log(data)) // DO stuff with Response
//     .catch(error => console.error(error));
// }

function getTickets(event) {
  const path = '/tasks';
  const url = baseURL + path;


  fetch(url)
    .then(response => response.json())
    .then(data => 
      {
        clearTickets();
        displayJson(JSON.stringify(data));
        updateDynamicButtons();
      }) // Do stuff with response
    .catch(error => console.error(error));
}



function putTicket(event) {
  event.preventDefault();
  // Need to get ticket ID
  console.log("Put ticket called");
  const form = event.target.parentNode;
  const formData = new FormData(form);
  const path = "/tasks/${ticketID}";
  const url = baseURL + path;
  const options = {
    method: 'PUT',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(Object.fromEntries(formData))
  };
  // Make API call
}

function postTicket(event) {
  event.preventDefault();
  console.log("Post ticket called");
  const form = event.target.parentNode;
  console.log(form)
  const formData = new FormData(form);
  const path = '/tasks';
  const url = baseURL + path;
  const options = {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(Object.fromEntries(formData))
  };
  console.log(options);

  fetch(url, options)
    .then(response => response.json())
    .then(data => {
      console.log(data);
      form.reset();
    }) 
    .catch(error => console.error(error));
}

function attemptLogin(event) {
  console.log("Attempt login called")
  event.preventDefault();
  const form = event.target.parentNode;
  const formData = new FormData(form);
  const id = formData.get("id");

  const path = "/login/" + id;
  const url = baseURL + path;

  const options = {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(Object.fromEntries(formData))
  };

  console.log(JSON.stringify(Object.fromEntries(formData)));
  // if valid response, set global variable userID to id entered 
}

// function unsubTicket() {
//   //  Need to get ticket ID from inbox for ticket
//   const path = '/tasks/${ticketID}';
//   const url = baseURL + path;
//   const options = {
//     method: 'PUT',
//     headers: {'Content-Type': 'application/json'},
//     body: JSON.stringify(Object.fromEntries(formData))
//   };

//   fetch(url, options)
//     .then(response => response.json())
//     .then(data => console.log(data)) // Do Stuff with response
//     .catch(error => console.error(error));
// }

function subTicket(event) {
  event.preventDefault();
  var ticketID = event.srcElement.parentNode.parentNode.id;
  const path = '/tasks/${ticketID}';
  const url = baseURL + path;
  const options = {
    method: 'PUT',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(Object.fromEntries(formData))
  };

  fetch(url, options)
    .then(response => response.json())
    .then(data => console.log(data)) // Do Stuff with response
    .catch(error => console.error(error));
}

// THis one may need to be changed, No form really for this one. Will just need user id and ticket id
function claimTicket(event) {
  event.preventDefault();
  var ticketID = event.srcElement.parentNode.parentNode.id;
  const claimData = {
    assignee: '${userID}'
  };
  const path = '/tasks/${ticketID}';
  const url = baseURL + path;
  const options = {
    method: 'PUT',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(claimData)
  };

  fetch(url, options)
    .then(response => response.json())
    .then(data => console.log(data)) // Do Stuff with response
    .catch(error => console.error(error));
}



function deleteTicket(event) {
  event.preventDefault();
  var ticketID = event.srcElement.parentNode.parentNode.id;
  console.log("Delete button pressed");
  console.log(ticketID);
  const path = '/tasks/' + ticketID;
  const url = baseURL + path;
  const options = {
    method: 'DELETE'
  };

  console.log(path);

  fetch(url, options)
    .then(response => response.json())
    .then(data => {
      getTickets(); 
    }) // Do Stuff with response
    .catch(error => console.error(error));
}

