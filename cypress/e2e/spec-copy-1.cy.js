describe('Register Test', () => {
    it('passes', () => {
      cy.visit('http://10.8.89.240:8080/cook/register');

      cy.get('input[x-model="formData.phone"]').type('18328656115');

      cy.get('input[x-model="formData.username"]').type('ckj');

      cy.get('input[x-model="formData.password"]').type('123456');

      cy.get('button[type="submit"]').click();

      cy.url().should('include', '/login');

 
    cy.visit('http://10.8.89.240:8080/cook/login'); 

    cy.get('input[name="phone"]').type('18328656115');

    cy.get('input[name="password"]').type('123456');

    cy.get('button[type="submit"]').click();

     cy.url().should('include', '/home'); 



    })

  })