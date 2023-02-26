const userID = 123;

// Get the HTML elements to show/hide
const createTicketSection = document.getElementById("create-ticket");
const viewTicketsSection = document.getElementById("view-tickets");
const inboxSection = document.getElementById("inbox");

// Get the elements for REST API calls
const myInbox = document.getElementById("my-inbox");
const allTickets = document.getElementById("all-tickets");
const createNewTicket = document.getElementById("submitTicket");
const unsubscribe = document.getElementById("unsubscribe");
const subscribe = document.getElementById("subscribe");
const del = document.getElementById("deleteTicket");
const claim = document.getElementById("claim");

// Get the menu items
const createTicketLink = document.querySelector("a[href='#create-ticket']");
const viewTicketsLink = document.querySelector("a[href='#view-tickets']");
const inboxLink = document.querySelector("a[href='#inbox']");

// Hide all sections except for the Create a Ticket section initially
viewTicketsSection.style.display = "none";
inboxSection.style.display = "none";

// Attach click event listeners to switch scenes
createTicketLink.addEventListener("click", function(event) {
  event.preventDefault();
  createTicketSection.style.display = "block";
  viewTicketsSection.style.display = "none";
  inboxSection.style.display = "none";
});

viewTicketsLink.addEventListener("click", function(event) {
  event.preventDefault();
  createTicketSection.style.display = "none";
  viewTicketsSection.style.display = "block";
  inboxSection.style.display = "none";
});

inboxLink.addEventListener("click", function(event) {
  event.preventDefault();
  createTicketSection.style.display = "none";
  viewTicketsSection.style.display = "none";
  inboxSection.style.display = "block";
});

// Add listeners
myInbox.addEventListener('click', getInbox);
allTickets.addEventListener('click', getTickets);
createNewTicket.addEventListener('click', postTicket);
unsubscribe.addEventListener('click', unsubTicket);
subscribe.addEventListener('click', subTicket);
del.addEventListener('click', deleteTicket);
claim.addEventListener('click', claimTicket);

const baseURL = '127.0.0.1:8080/api/v1'; // This needs to be updated and set if changed

// Functions

function getInbox() {
  const path = '/inbox/${userID}';
  const url = baseURL + path;

  fetch(url)
    .then(response => response.json())
    .then(data => console.log(data)) // DO stuff with Response
    .catch(error => console.error(error));
}

function getTickers() {
  const path = '/tasks';
  const url = baseURL + path;

  fetch(url)
    .then(response => response.json())
    .then(data => console.log(data)) // Do stuff with response
    .catch(error => console.error(error));
}

function postTicket(event) {
  event.preventDefault();
  const form = event.target;
  const formData = new FormData(form);
  const path = '/tasks';
  const url = baseURL + path;
  const options = {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(Object.fromEntries(formData))
  };

  fetch(url, options)
    .then(response => response.json())
    .then(data => console.log(data)) // Do Stuff with response
    .catch(error => console.error(error));
}

function unsubTicket(event) {
  event.preventDefault();
  const form = event.target;
  const formData = new FormData(form);
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

function subTicket(event) {
  event.preventDefault();
  const form = event.target;
  const formData = new FormData(form);
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
function claimTicket() {
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

function deleteTicket() {
  const path = '/tasks/${ticketID}';
  const url = baseURL + path;
  const options = {
    method: 'DELETE',
  };

  fetch(url, options)
    .then(response => response.json())
    .then(data => console.log(data)) // Do Stuff with response
    .catch(error => console.error(error));
}
