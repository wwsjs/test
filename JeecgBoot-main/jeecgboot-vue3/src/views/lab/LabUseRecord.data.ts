import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '使用人ID',
    align:"center",
    dataIndex: 'userId_dictText'
   },
   {
    title: '设备ID',
    align:"center",
    dataIndex: 'equipmentId_dictText'
   },
   {
    title: '依托项目ID',
    align:"center",
    dataIndex: 'projectId_dictText'
   },
   {
    title: '开始日期',
    align:"center",
    dataIndex: 'startDate',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
   },
   {
    title: '结束日期',
    align:"center",
    dataIndex: 'endDate',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
   },
   {
    title: '用途或项目内容',
    align:"center",
    dataIndex: 'purpose'
   },
   {
    title: '实际使用时间/小时',
    align:"center",
    dataIndex: 'actualHours'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '使用人ID',
    field: 'userId',
    component: 'JSearchSelect',
    componentProps:{
       dict:"lab_user,username,id"
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入使用人ID!'},
          ];
     },
  },
  {
    label: '设备ID',
    field: 'equipmentId',
    component: 'JSearchSelect',
    componentProps:{
       dict:"lab_equipment,equipment_name,id"
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入设备ID!'},
          ];
     },
  },
  {
    label: '依托项目ID',
    field: 'projectId',
    component: 'JSearchSelect',
    componentProps:{
       dict:"lab_project,project_name,id"
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入依托项目ID!'},
          ];
     },
  },
  {
    label: '开始日期',
    field: 'startDate',
    component: 'DatePicker',
    componentProps: {
      valueFormat: 'YYYY-MM-DD'
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入开始日期!'},
          ];
     },
  },
  {
    label: '结束日期',
    field: 'endDate',
    component: 'DatePicker',
    componentProps: {
      valueFormat: 'YYYY-MM-DD'
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入结束日期!'},
          ];
     },
  },
  {
    label: '用途或项目内容',
    field: 'purpose',
    component: 'InputTextArea',
  },
  {
    label: '实际使用时间/小时',
    field: 'actualHours',
    component: 'InputNumber',
    componentProps: {
      min: 0.1,
      precision: 2,
      step: 0.5,
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入实际使用时间/小时!'},
          ];
     },
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];

// 高级查询数据
export const superQuerySchema = {
  userId: {title: '使用人ID',order: 0,view: 'sel_search', type: 'string',dictTable: "lab_user", dictCode: 'id', dictText: 'username',},
  equipmentId: {title: '设备ID',order: 1,view: 'sel_search', type: 'string',dictTable: "lab_equipment", dictCode: 'id', dictText: 'equipment_name',},
  projectId: {title: '依托项目ID',order: 2,view: 'sel_search', type: 'string',dictTable: "lab_project", dictCode: 'id', dictText: 'project_name',},
  startDate: {title: '开始日期',order: 3,view: 'date', type: 'string',},
  endDate: {title: '结束日期',order: 4,view: 'date', type: 'string',},
  purpose: {title: '用途或项目内容',order: 5,view: 'textarea', type: 'string',},
  actualHours: {title: '实际使用时间/小时',order: 6,view: 'number', type: 'number',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
