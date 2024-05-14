describe('Register Test', () => {
    it('passes', () => {
      cy.visit('http://localhost:8080/cook/register');

      cy.get('input[x-model="formData.phone"]').type('18328111111');

      cy.get('input[x-model="formData.username"]').type('ckj');

      cy.get('input[x-model="formData.password"]').type('123456');

      cy.get('button[type="submit"]').click();

      cy.url().should('include', '/login');

    })

  })