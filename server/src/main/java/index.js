const mysql = require('mysql');
const express = require('express');
var app = express();
const bodyparser = require('body-parser');

app.use(bodyparser.json());

var mysqlConnection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '1234',
    database: 'EmployeeDB',
    multipleStatements: true
});

mysqlConnection.connect((err) => {
    if (!err)
        console.log('DB connection succeded.');
    else
        console.log('DB connection failed \n Error : ' + JSON.stringify(err, undefined, 2));
});


app.listen(3000, () => console.log('Express server is runnig at port no : 3000'));


//Get all employees
app.get('/employees', (req, res) => {
    mysqlConnection.query('SELECT * FROM Customer', (err, rows, fields) => {
        if (!err)
            res.send(rows);
        else
            console.log(err);
    })
});

//Get an customers
app.get('/customers/:id', (req, res) => {
    mysqlConnection.query('SELECT * FROM Customer WHERE ID = ?', [req.params.id], (err, rows, fields) => {
        if (!err)
            res.send(rows);
        else
            console.log(err);
    })
});

//Delete an customer
app.delete('/customers/:id', (req, res) => {
    mysqlConnection.query('DELETE FROM Customer WHERE ID = ?', [req.params.id], (err, rows, fields) => {
        if (!err)
            res.send('Deleted successfully.');
        else
            console.log(err);
    })
});

//Insert an customer
app.post('/customers', (req, res) => {
    let emp = req.body;
    var sql = "SET @ID = ?;SET @Name = ?;SET @Country_of_birth = ?;SET @Country_of_residence = ?;SET @Segment = ?; \
    CALL EmployeeAddOrEdit(@ID,@Name,@Country_of_birth,@Country_of_residence,@Segment);";
    mysqlConnection.query(sql, [emp.ID, emp.Name, emp.Country_of_birth, emp.Country_of_residence, emp.Segment], (err, rows, fields) => {
        if (!err)
            rows.forEach(element => {
                if(element.constructor == Array)
                res.send('Inserted customer id : '+element[0].ID);
            });
        else
            console.log(err);
    })
});

//Update an customers
app.put('/customers', (req, res) => {
    let emp = req.body;
    var sql = "SET @ID = ?;SET @Name = ?;SET @Country_of_birth = ?;SET @Country_of_residence = ?;SET @Segment = ?; \
    CALL EmployeeAddOrEdit(@ID,@Name,@Country_of_birth,@Country_of_residence,@Segment);";
    mysqlConnection.query(sql, [emp.ID, emp.Name, emp.Country_of_birth, emp.Country_of_residence, emp.Segment], (err, rows, fields) => {
        if (!err)
            res.send('Updated successfully');
        else
            console.log(err);
    })
});
