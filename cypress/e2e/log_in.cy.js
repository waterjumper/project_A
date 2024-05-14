describe('Login Test', () => {
  it('Visits the login page and logs in', () => {
    cy.visit('http://localhost:8080/cook/login'); 

    cy.get('input[name="phone"]').type('1833');

    cy.get('input[name="password"]').type('123456');

    cy.get('button[type="submit"]').click();

     cy.url().should('include', '/home'); 

  });


});